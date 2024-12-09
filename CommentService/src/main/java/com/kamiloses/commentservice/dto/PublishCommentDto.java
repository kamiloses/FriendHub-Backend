package com.kamiloses.commentservice.dto;

import lombok.Data;


@Data

public class PublishCommentDto {


    private String content;


    private UserDetailsDto userDetails;

    private String postId;

    private String parentCommentId;


}
