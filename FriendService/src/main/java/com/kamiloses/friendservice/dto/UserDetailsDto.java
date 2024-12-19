package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDto {


    private String id;

    private String chatId;

    private String username;

    private Boolean isOnline;

    private String firstName;

    private String lastName;

}
