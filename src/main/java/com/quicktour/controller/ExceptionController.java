package com.quicktour.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for exception handling
 *
 * @author Roman Lukash
 */
@ControllerAdvice
public class ExceptionController {
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ModelAndView handleMethodNotSupported(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleDenied() {
        return "403";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: {}  raised {}", req.getRequestURL(), exception);
        for (StackTraceElement element : exception.getStackTrace()) {
            logger.error(element.toString());
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "Sorry, something went wrong");
        modelAndView.addObject("exception", exception);
        modelAndView.addObject("url", req.getRequestURL());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
