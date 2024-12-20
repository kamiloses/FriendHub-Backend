package com.kamiloses.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
public class SendMessageDto {

    private String chatId;

    private String senderUsername;

    private String content;

}
