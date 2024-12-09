package com.kamiloses.commentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class CommentDto {


    private String id;
    private String content;
    private Date createdAt;
    private UserDetailsDto userDetails;
    private String postId;
    private String parentCommentId;
    private Integer numberOfComments;


}
