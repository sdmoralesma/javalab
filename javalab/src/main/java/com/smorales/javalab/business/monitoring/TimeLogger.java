package com.smorales.javalab.business.monitoring;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.time.Duration;
import java.time.Instant;
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
            tracer.info(() -> "Total Duration of '" + ctx.getMethod().getName() + "' method, was: " + Duration.between(start, end).toMillis() + " milliseconds");
        }
    }
}
