package com.logiweb.avaji.exceptions;


public class LoadAndUnloadValidateException extends Exception {
    public LoadAndUnloadValidateException() {
    }

    public LoadAndUnloadValidateException(String message) {
        super(message);
    }

    public LoadAndUnloadValidateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadAndUnloadValidateException(Throwable cause) {
        super(cause);
    }

}
