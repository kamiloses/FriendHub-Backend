package com.kamiloses.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SendMessageDto {

    private String chatId;

    private String senderUsername;

    private String content;

}
