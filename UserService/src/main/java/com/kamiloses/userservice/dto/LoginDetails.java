package com.kamiloses.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDetails {

    private String username;
    private String password;



}