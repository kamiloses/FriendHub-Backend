package com.kamiloses.messageservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MessageExceptionHandler {

    public static final String MESSAGE_DATABASE_ERROR = "Problem with message fetching/adding to the database";

    @ExceptionHandler(MessageDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleMessageDatabaseError(MessageDatabaseFetchException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: " + exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                MESSAGE_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
