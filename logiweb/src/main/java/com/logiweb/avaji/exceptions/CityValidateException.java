package com.logiweb.avaji.exceptions;

public class CityValidateException extends Exception {
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
