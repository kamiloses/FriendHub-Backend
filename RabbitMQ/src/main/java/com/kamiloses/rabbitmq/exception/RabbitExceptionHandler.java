package com.kamiloses.rabbitmq.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
@Slf4j
public class RabbitExceptionHandler {


    public static final String RABBITMQ_CONNECTION_ERROR = "RABBITMQ_CONNECTION_ERROR";

    @ExceptionHandler({AmqpException.class, TimeoutException.class})
    public ResponseEntity<ErrorResponse> handleRabbitMQException(Exception exception) {
        log.error("RabbitMQ connection error: {}", exception.getMessage());


         ErrorResponse errorResponse = new ErrorResponse(
                 "RabbitMQ connection error: "+exception.getMessage(),
                 java.time.LocalDateTime.now().toString(),
                 RABBITMQ_CONNECTION_ERROR
         );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);}
}


