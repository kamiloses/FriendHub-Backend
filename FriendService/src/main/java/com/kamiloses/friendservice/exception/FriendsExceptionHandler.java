package com.kamiloses.friendservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class FriendsExceptionHandler {


    public static final String FRIENDS_DATABASE_ERROR = "Problem with friends fetching/adding to the database";

    @ExceptionHandler(FriendsDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleFriendsDatabaseError(FriendsDatabaseFetchException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: " + exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                FRIENDS_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }








}
