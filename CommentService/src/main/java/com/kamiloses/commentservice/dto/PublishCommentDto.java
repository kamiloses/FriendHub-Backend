package com.kamiloses.commentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
public class PublishCommentDto {


    private String content;

    private String postId;

    private String parentCommentId;




}
