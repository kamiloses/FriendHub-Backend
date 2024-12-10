package com.kamiloses.messageservice.dto;

import lombok.Data;

import java.util.Date;

@Data

public class SendMessageDto {

    private String chatId;

    private String senderUsername;

    private String content;

}
