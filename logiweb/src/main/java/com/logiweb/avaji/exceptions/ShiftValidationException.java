package com.logiweb.avaji.exceptions;

public class ShiftValidationException extends Exception {

    public ShiftValidationException() {
    }

    public ShiftValidationException(String message) {
        super(message);
    }

    public ShiftValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiftValidationException(Throwable cause) {
        super(cause);
    }
}
