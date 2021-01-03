package com.logiweb.avaji.exceptions;

public class CityValidateException extends RuntimeException {
    public CityValidateException() {
    }

    public CityValidateException(String message) {
        super(message);
    }

    public CityValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityValidateException(Throwable cause) {
        super(cause);
    }

}
