package com.logiweb.avaji.exceptionhandler;

import com.logiweb.avaji.exceptions.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;

@ControllerAdvice
public class LocalExceptionHandler {

    @ExceptionHandler(NoResultException.class)
    public String queryExceptionHandle(NoResultException exception,
                                       Model model) {
        model.addAttribute("message", exception.getMessage());
        return "exceptions/details";
    }

    @ExceptionHandler({CityValidateException.class, LoadAndUnloadValidateException.class})
    public String orderExceptionHandler(Exception exception,
                                               Model model) {
        model.addAttribute("message", exception.getMessage());
        return "exceptions/order";
    }

    @ExceptionHandler(ShiftSizeExceedException.class)
    public String appointingExceptionHandler(ShiftSizeExceedException exception,
                                            Model model) {
        model.addAttribute("message", exception.getMessage());
        return "exceptions/appointing";
    }

    @ExceptionHandler({ShiftValidationException.class, DriverStatusNotFoundException.class})
    public String workDetailsExceptionHandler(ShiftValidationException exception,
                                             Model model) {
        model.addAttribute("message", exception.getMessage());
        return "exceptions/details";
    }
}
