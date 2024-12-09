package com.kamiloses.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Stream;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class UserDetailsDto {


    private String id;
    private String chatId;
    private String username;

    private String password;


    private String firstName;
    private String lastName;






    }

