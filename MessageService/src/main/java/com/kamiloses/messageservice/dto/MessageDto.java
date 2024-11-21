package com.kamiloses.messageservice.dto;

import lombok.Data;

import java.util.Date;
@Data
public class MessageDto {


    private UserDetailsDto sender;
    private UserDetailsDto recipient;

    private String content;

    private Date timestamp;

    private boolean isRead = false;
}
