package com.kamiloses.postservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsDto {


    private String id;
    private String username;
    private String chatId;

    private Boolean isOnline;

    private String firstName;
    private String lastName;


}
