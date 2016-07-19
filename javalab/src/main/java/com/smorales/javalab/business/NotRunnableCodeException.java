package com.smorales.javalab.business;

public class NotRunnableCodeException extends RuntimeException {

    public NotRunnableCodeException(String message) {
        super(message);
    }

    public NotRunnableCodeException(Throwable cause) {
        super(cause);
    }

    public NotRunnableCodeException(String message, Throwable cause) {
        super(message, cause);
    }

}
