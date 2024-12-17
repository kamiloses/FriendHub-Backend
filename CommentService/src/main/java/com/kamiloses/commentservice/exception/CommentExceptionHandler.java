package com.kamiloses.commentservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CommentExceptionHandler {



    public static final String COMMENT_DATABASE_ERROR = "Problem with comment fetching/adding to the database";

    @ExceptionHandler(CommentDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleCommentDatabaseError(CommentDatabaseFetchException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: " + exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                COMMENT_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }
}


