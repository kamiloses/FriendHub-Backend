package com.kamiloses.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
        log.error("some error has just occurred with bad data");
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


}
