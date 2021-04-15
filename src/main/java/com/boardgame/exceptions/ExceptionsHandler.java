package com.boardgame.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler({NotFoundGameException.class, NotFoundActionException.class, InvalidActionException.class})
    public ResponseEntity<ExceptionDTO> notFoundGame(Exception e) {
        ExceptionDTO exception = new ExceptionDTO();
        exception.setCode(HttpStatus.BAD_REQUEST.value());
        exception.setMessage(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionDTO> badRequest(Exception e) {
        ExceptionDTO exception = new ExceptionDTO();
        exception.setCode(HttpStatus.BAD_REQUEST.value());
        exception.setMessage(e.getCause().getLocalizedMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> unExpectedException(Exception e) {
        LOGGER.error("Unexpected exception", e);
        ExceptionDTO exception = new ExceptionDTO();
        exception.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        exception.setMessage(e.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }
}
