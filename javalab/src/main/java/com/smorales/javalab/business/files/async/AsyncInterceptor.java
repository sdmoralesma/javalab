package com.smorales.javalab.business.files.async;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.logging.Logger;

import static java.lang.Boolean.TRUE;
import static javax.interceptor.Interceptor.Priority.PLATFORM_BEFORE;

/**
 * Create a Async annotation compatible with CDI
 * Copied from: http://jdevelopment.nl/cdi-based-asynchronous-alternative/
 */
@Async
@Interceptor
@Priority(PLATFORM_BEFORE)
public class AsyncInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final ThreadLocal<Boolean> asyncInvocation = new ThreadLocal<>();

    @Inject
    Logger tracer;

    @Resource
    private ManagedExecutorService managedExecutorService;

    @AroundInvoke
    public synchronized Object submitAsync(InvocationContext ctx) throws Exception {
        Instant start = Instant.now();
        if (TRUE.equals(asyncInvocation.get())) {
            return ctx.proceed();
        }

        return new FutureDelegator(managedExecutorService.submit(() -> {
            try {
                asyncInvocation.set(TRUE);
                return ctx.proceed();
            } finally {
                asyncInvocation.remove();
                traceTotalTime(ctx, start);
            }
        }));

    }

    private void traceTotalTime(InvocationContext ctx, Instant start) {
        Instant end = Instant.now();
        tracer.info(() -> {
            Long duration = Duration.between(start, end).toMillis();
            return new StringJoiner("")
                    .add("ASYNC Total Duration of method ")
                    .add(ctx.getMethod().getName())
                    .add(" on class ")
                    .add(ctx.getMethod().getDeclaringClass().getSimpleName())
                    .add(" : ").add(duration.toString()).add(" milliseconds")
                    .toString();
        });
    }
}
