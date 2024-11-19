package com.kamiloses.authservice.security.jwt;

import lombok.Data;

@Data
public class AuthRequest {

    private String username;
    private String password;

    // Getters and setters
}