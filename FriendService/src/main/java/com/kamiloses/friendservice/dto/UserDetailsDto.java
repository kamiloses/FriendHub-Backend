package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {


    private String id;
    private String username;

    private String password;

    private String firstName;
    private String lastName;


    private String profileImageUrl;



}
