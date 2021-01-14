package com.logiweb.avaji.exceptionhandler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;



@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String pageNotFoundException() {
        return "exceptions/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String pagePermissionDeniedException(){
        return "exceptions/403";
    }
//TODO: return after testing
//    @ExceptionHandler(Exception.class)
//    public String serverException() {
//        return "exceptions/500";
//    }
}
