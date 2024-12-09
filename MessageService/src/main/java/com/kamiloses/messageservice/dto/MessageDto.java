package com.kamiloses.messageservice.dto;

import lombok.Data;

import java.util.Date;
@Data
public class MessageDto {
    private String chatId;

    private UserDetailsDto sender;

    private String content;

    private Date createdAt;

    private boolean isRead = false;
}
