package com.kamiloses.commentservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PublishCommentDto {


    private String content;

    private Date createdAt;

    private UserDetailsDto userDetails;

    private String postId;

    private String parentCommentId;


}
