package com.kamiloses.authservice.security.jwt;

import lombok.Data;

@Data
public class LoginDetails {

    private String username;
    private String password;

    // Getters and setters
}