package com.boardgame.exceptions;

public class NotFoundActionException extends RuntimeException {

    public NotFoundActionException(String message) {
        super(message);
    }
}
