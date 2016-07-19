package com.smorales.javalab.business.tracing;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

public class LoggerExposer {

    @Produces
    public Logger expose(InjectionPoint ip) {
        String loggerName = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(loggerName);
    }

}

