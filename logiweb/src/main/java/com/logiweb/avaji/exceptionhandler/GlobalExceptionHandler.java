package com.logiweb.avaji.exceptionhandler;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String pageNotFoundException() {
        return "exceptions/404";
    }

    @ExceptionHandler(Exception.class)
    public String serverException() {
        return "exceptions/500";
    }
}
