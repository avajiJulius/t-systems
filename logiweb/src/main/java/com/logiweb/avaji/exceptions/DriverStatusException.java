package com.logiweb.avaji.exceptions;

public class DriverStatusException extends RuntimeException {
    public DriverStatusException() {
    }

    public DriverStatusException(String message) {
        super(message);
    }

    public DriverStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverStatusException(Throwable cause) {
        super(cause);
    }
}
