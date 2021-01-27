package com.logiweb.avaji.exceptions;

public class UniqueValidationException extends RuntimeException {
    public UniqueValidationException(String message) {
    }

    public UniqueValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueValidationException(Throwable cause) {
        super(cause);
    }

    public UniqueValidationException() {
    }
}
