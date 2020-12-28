package com.logiweb.avaji.exceptions;

public class CargoStatusException extends RuntimeException {
    public CargoStatusException() {
    }

    public CargoStatusException(String message) {
        super(message);
    }

    public CargoStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public CargoStatusException(Throwable cause) {
        super(cause);
    }
}
