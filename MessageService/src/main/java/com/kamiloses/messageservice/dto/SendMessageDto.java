package com.kamiloses.messageservice.dto;

import lombok.Data;


@Data

public class SendMessageDto {

    private String chatId;

    private String senderUsername;

    private String content;

}
