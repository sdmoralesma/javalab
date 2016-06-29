package com.smorales.javalab.workspaceprocessor.boundary;

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
