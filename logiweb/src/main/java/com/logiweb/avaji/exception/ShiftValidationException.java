package com.logiweb.avaji.exception;

public class ShiftValidationException extends RuntimeException {

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
