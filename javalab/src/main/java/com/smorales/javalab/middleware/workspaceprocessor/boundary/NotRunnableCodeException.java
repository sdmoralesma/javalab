package com.smorales.javalab.middleware.workspaceprocessor.boundary;

public class NotRunnableCodeException extends RuntimeException {

    public NotRunnableCodeException(String message) {
        super(message);
    }

    public NotRunnableCodeException(Throwable cause) {
        super(cause);
    }

}
