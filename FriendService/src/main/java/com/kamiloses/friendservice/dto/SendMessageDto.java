package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SendMessageDto {
    private String chatId;
    private String message;
    private String username;
    private String firstName;
    private String lastName;
}
