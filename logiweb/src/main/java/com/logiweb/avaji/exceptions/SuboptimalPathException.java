package com.logiweb.avaji.exceptions;

public class SuboptimalPathException extends RuntimeException {
    public SuboptimalPathException() {
    }

    public SuboptimalPathException(String message) {
        super(message);
    }

    public SuboptimalPathException(String message, Throwable cause) {
        super(message, cause);
    }

    public SuboptimalPathException(Throwable cause) {
        super(cause);
    }
}
