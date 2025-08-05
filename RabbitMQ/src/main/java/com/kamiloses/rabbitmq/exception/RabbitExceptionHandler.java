package com.kamiloses.rabbitmq.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.concurrent.TimeoutException;

@ControllerAdvice
@Slf4j
public class RabbitExceptionHandler {


    public static final String RABBITMQ_CONNECTION_ERROR = "RABBITMQ_CONNECTION_ERROR";
    public static final String UPSTREAM_SERVICE_ERROR = "UPSTREAM_SERVICE_ERROR";
    @ExceptionHandler({AmqpException.class, TimeoutException.class})
    public ResponseEntity<ErrorResponse> handleRabbitMQException(Exception exception) {
        log.error("RabbitMQ connection error: {}", exception.getMessage());


         ErrorResponse errorResponse = new ErrorResponse(
                 "RabbitMQ connection error: "+exception.getMessage(),
                 java.time.LocalDateTime.now().toString(),
                 RABBITMQ_CONNECTION_ERROR
         );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);}


    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> handleWebClientResponseException(WebClientResponseException ex) {
        log.error("WebClient returned error: status={}, body={}", ex.getRawStatusCode(), ex.getResponseBodyAsString());

        ErrorResponse errorResponse = new ErrorResponse(
                "Upstream service error: " + ex.getStatusText(),
                java.time.LocalDateTime.now().toString(),
                UPSTREAM_SERVICE_ERROR
        );

        return ResponseEntity.status(ex.getRawStatusCode()).body(errorResponse);
    }

}


