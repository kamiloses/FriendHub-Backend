package com.kamiloses.messageservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class MessageEntity {


    @Id
    private String id;

    private String chatId;//friendship entity ID
    private String senderUsername;
    private String recipientUsername;

    private String content;

    private Date timestamp;

    private boolean isRead = false;


}