package com.kamiloses.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MessageDto {
    private String chatId;

    private UserDetailsDto sender;

    private String content;

    private Date createdAt;

    private boolean isRead = false;
}
