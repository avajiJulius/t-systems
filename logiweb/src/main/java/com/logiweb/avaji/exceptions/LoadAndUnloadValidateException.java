package com.logiweb.avaji.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class LoadAndUnloadValidateException extends RuntimeException {
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
