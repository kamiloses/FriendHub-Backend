package com.kamiloses.postservice.exception;

public class RabbitDoesNotWorkException extends RuntimeException {
    public RabbitDoesNotWorkException(String message) {
        super(message);
    }
}
