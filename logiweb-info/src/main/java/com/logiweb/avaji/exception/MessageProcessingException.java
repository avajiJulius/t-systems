package com.logiweb.avaji.exception;

public class MessageProcessingException extends RuntimeException {
    public MessageProcessingException() {
        super();
    }

    public MessageProcessingException(String message) {
        super(message);
    }

    public MessageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageProcessingException(Throwable cause) {
        super(cause);
    }
}
