package com.kamiloses.postservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class PostExceptionHandler {



    public static final String POST_DATABASE_ERROR = "Problem with post fetching/adding to the database";


    @ExceptionHandler(PostDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handlePostDatabaseError(PostDatabaseFetchException exception) {


        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: "+exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                POST_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);}
}


