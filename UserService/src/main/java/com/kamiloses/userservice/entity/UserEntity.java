package com.kamiloses.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String id;


    private String username;

    private String password;


    private String firstName;
    private String lastName;



}
