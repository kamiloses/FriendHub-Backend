package com.kamiloses.commentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

@Data

public class PublishCommentDto {


    private String content;


    private UserDetailsDto userDetails;

    private String postId;

    private String parentCommentId;


}
