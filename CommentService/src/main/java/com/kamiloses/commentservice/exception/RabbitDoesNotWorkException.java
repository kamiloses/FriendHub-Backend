package com.kamiloses.commentservice.exception;

public class RabbitDoesNotWorkException extends RuntimeException {
    public RabbitDoesNotWorkException(String message) {
        super(message);
    }
}
