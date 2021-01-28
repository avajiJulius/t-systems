package com.logiweb.avaji.exceptionhandler;

import com.logiweb.avaji.exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
public class LocalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(LocalExceptionHandler.class);

    @ExceptionHandler({LoadAndUnloadValidateException.class})
    public String orderExceptionHandler(LoadAndUnloadValidateException exception,
                                        Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        return "exceptions/order";
    }

    @ExceptionHandler({PathParseException.class})
    public String pathParseExceptionHandler(PathParseException exception,
                                            Model model) {
        logger.error(exception.getMessage());
        model.addAttribute("message", exception.getMessage());
        return "redirect:/";
    }


    @ExceptionHandler({SuboptimalPathException.class})
    public String suboptimalPathExceptionHandler(SuboptimalPathException exception,
                                                 RedirectAttributes attributes) {
        logger.error(exception.getMessage());
        attributes.addFlashAttribute("message", exception.getMessage());
        return "redirect:/orders/new";
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
