package com.logiweb.avaji.exceptions;

public class PathParseException extends RuntimeException {

    public PathParseException() {
    }

    public PathParseException(String message) {
        super(message);
    }

    public PathParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public PathParseException(Throwable cause) {
        super(cause);
    }
}
