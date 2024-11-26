package com.kamiloses.likeservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
 @AllArgsConstructor @NoArgsConstructor
public class LikeEntity {

    private String id;
    private String username;
    private String postId;
    private String commentId;
    private Date  createdAt;




}
