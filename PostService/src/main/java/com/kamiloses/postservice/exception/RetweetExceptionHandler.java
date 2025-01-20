package com.kamiloses.postservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RetweetExceptionHandler {

    public static final String RETWEET_DATABASE_ERROR = "Problem with retweet fetching/adding to the database";

    @ExceptionHandler(RetweetDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleRetweetDatabaseError(RetweetDatabaseFetchException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: " + exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                RETWEET_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }
}
