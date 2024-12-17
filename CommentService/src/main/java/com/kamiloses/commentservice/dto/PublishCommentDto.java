package com.kamiloses.commentservice.dto;

import lombok.Data;


@Data

public class PublishCommentDto {


    private String content;

    private String postId;

    private String parentCommentId;


}
