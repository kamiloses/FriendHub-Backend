package com.kamiloses.userservice.exception;

import com.kamiloses.rabbitmq.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleRegistrationException(MethodArgumentNotValidException e) {
        log.error("some error has just occurred with bad data written in the registration inputs");
        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        System.err.println(errors);
        return ResponseEntity.badRequest().body(errors);
    }





    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<List<String>> handleUsernameAlreadyExistsException() {

        return ResponseEntity.badRequest().body(List.of("this Username already exists"));
    }









    public static final String USER_DATABASE_ERROR = "Problem with user fetching/adding to the database";

    @ExceptionHandler(UserDatabaseFetchException.class)
    public ResponseEntity<ErrorResponse> handleUserDatabaseError(UserDatabaseFetchException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                "Database error: " + exception.getMessage(),
                java.time.LocalDateTime.now().toString(),
                USER_DATABASE_ERROR
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse);
    }








}
