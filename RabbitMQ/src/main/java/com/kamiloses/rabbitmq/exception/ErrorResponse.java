package com.kamiloses.rabbitmq.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class ErrorResponse {


    private String message;
    private String timestamp;
    private String status;

}
