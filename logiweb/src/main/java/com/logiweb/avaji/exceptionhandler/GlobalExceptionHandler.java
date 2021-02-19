package com.logiweb.avaji.exceptionhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String pageNotFoundException() {
        return "exceptions/404";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String pagePermissionDeniedException(AccessDeniedException e){
        logger.error("Access Denied: {}", e.getMessage());
        return "exceptions/403";
    }

//    @ExceptionHandler(Exception.class)
//    public String pageException(Exception exception) {
//        logger.error(exception.getMessage());
//        return "exceptions/500";
//    }

}
