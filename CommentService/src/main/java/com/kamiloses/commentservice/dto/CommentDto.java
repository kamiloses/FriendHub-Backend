package com.kamiloses.commentservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {


    private String id;
    private String content;
    private Date createdAt;
    private UserDto userId;
    private String postId;
    private String parentCommentId;
    private Integer numberOfComments;
    private Integer numberOfLikes;
    private Integer numberOfReplies;


}
