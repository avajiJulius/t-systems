package com.logiweb.avaji.exceptionhandler;

import com.logiweb.avaji.exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class LocalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(LocalExceptionHandler.class);

    @ExceptionHandler({LoadAndUnloadValidateException.class})
    public String orderExceptionHandler(Exception exception,
                                        Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        return "exceptions/order";
    }

    @ExceptionHandler(ShiftSizeExceedException.class)
    public String appointingExceptionHandler(ShiftSizeExceedException exception,
                                            Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        return "exceptions/appointing";
    }

    @ExceptionHandler({ShiftValidationException.class})
    public String workDetailsExceptionHandler(ShiftValidationException exception,
                                             Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        return "exceptions/details";
    }
}
