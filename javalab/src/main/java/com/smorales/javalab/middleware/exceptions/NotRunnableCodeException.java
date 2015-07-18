package com.smorales.javalab.middleware.exceptions;

public class NotRunnableCodeException extends RuntimeException {

    public NotRunnableCodeException() {
        super();
    }

    public NotRunnableCodeException(String message) {
        super(message);
    }

    public NotRunnableCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotRunnableCodeException(Throwable cause) {
        super(cause);
    }

    protected NotRunnableCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
