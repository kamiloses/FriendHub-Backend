package com.kamiloses.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {


    private String id;

    private UserDetailsDto user;

    private String content;

    private Date createdAt;

    private int likeCount = 0;

    private int commentCount = 0;

    private int retweetCount=0;

    private boolean isLikedByMe;
    private boolean isRetweetedByMe;

    private boolean isDeleted = false;

}
