package com.logiweb.avaji.exception;

public class UniqueValidationException extends RuntimeException {

    private String pathName;

    public String getPathName() {
        return pathName;
    }

    public UniqueValidationException(String message, String pathName) {
        super(message);
        this.pathName = pathName;
    }

    public UniqueValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UniqueValidationException(Throwable cause) {
        super(cause);
    }

}
