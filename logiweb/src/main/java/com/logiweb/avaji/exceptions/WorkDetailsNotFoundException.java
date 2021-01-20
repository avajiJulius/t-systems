package com.logiweb.avaji.exceptions;

public class WorkDetailsNotFoundException extends RuntimeException{
    public WorkDetailsNotFoundException() {
    }

    public WorkDetailsNotFoundException(String message) {
        super(message);
    }

    public WorkDetailsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorkDetailsNotFoundException(Throwable cause) {
        super(cause);
    }
}
