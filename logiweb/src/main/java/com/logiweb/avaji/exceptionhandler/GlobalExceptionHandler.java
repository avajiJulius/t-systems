package com.logiweb.avaji.exceptionhandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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

    @ExceptionHandler(ConstraintViolationException.class)
    public String pageOnUniqueValueException(ConstraintViolationException e, RedirectAttributes attributes) {
        logger.error(e.getMessage());
        attributes.addFlashAttribute("message", "Already contain this unique value");
        return "exceptions/details";
    }

}
