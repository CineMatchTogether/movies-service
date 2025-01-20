package com.service.movies.contollers.advice;

import com.service.movies.models.dto.ExceptionResponse;

import io.swagger.v3.oas.annotations.Hidden;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@Hidden
public class GlobalExceptionHandlerController {

    Logger logger = LogManager.getLogger(GlobalExceptionHandlerController.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
        logger.error("Exception handler", ex.fillInStackTrace());
        return new ResponseEntity<>(new ExceptionResponse(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
