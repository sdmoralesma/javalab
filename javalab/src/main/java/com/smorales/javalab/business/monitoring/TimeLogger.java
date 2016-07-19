package com.smorales.javalab.business.monitoring;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.time.Duration;
import java.time.Instant;
import java.util.StringJoiner;
import java.util.logging.Logger;

public class TimeLogger {

    @Inject
    Logger tracer;

    @AroundInvoke
    public Object logTime(InvocationContext ctx) throws Exception {
        Instant start = Instant.now();
        try {
            return ctx.proceed();
        } finally {
            Instant end = Instant.now();
            tracer.info(() -> {
                Long duration = Duration.between(start, end).toMillis();

                return new StringJoiner("")
                        .add("Total Duration of method ")
                        .add(ctx.getMethod().getName())
                        .add(" on class ")
                        .add(ctx.getMethod().getDeclaringClass().getSimpleName())
                        .add(" : ").add(duration.toString()).add(" milliseconds")
                        .toString();
            });
        }
    }
}
