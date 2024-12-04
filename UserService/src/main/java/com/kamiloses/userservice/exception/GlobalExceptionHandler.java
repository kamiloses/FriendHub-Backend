package com.kamiloses.userservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<String>> handleRegistrationException(WebExchangeBindException e) {
         log.error("some error has just occurred with bad data");
        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errors);
    }


    //todo popraw nazwy metod oraz logi
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<List<String>> handleUsernameAlreadyExistsException() {


        return ResponseEntity.badRequest().body(List.of("this Username already exists"));
    }


}
