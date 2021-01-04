package com.logiweb.avaji.exceptions;

public class DriverStatusNotFoundException extends Exception {
    public DriverStatusNotFoundException() {
    }

    public DriverStatusNotFoundException(String message) {
        super(message);
    }

    public DriverStatusNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverStatusNotFoundException(Throwable cause) {
        super(cause);
    }
}
