package com.eduardovecino.boardgame.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

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
}
