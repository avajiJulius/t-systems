package com.logiweb.avaji.exception;

public class ShiftSizeExceedException extends Exception {
    public ShiftSizeExceedException() {
    }

    public ShiftSizeExceedException(String message) {
        super(message);
    }

    public ShiftSizeExceedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShiftSizeExceedException(Throwable cause) {
        super(cause);
    }
}
