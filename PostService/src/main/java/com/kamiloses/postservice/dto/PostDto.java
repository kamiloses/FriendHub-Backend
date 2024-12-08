package com.kamiloses.postservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor

public class PostDto {


    private String id;

    private UserDetailsDto user;

    private String content;

    private Date createdAt;

    private int likeCount = 0;

    private int commentCount = 0;


    private boolean isDeleted = false;

}
