package com.kamiloses.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {


    private String id;
    private String username;

    private String password;

    private String role = "ROLE_USER";

    private String firstName;
    private String lastName;







}
