package com.kamiloses.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {


    private String id;
    private String username;
    private String chatId;


    private String firstName;
    private String lastName;

}
