package com.kamiloses.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class PostEntity {
    @Id
    private String id;

    private String userId;

    private String content;

    private Date createdAt;

    private int likeCount = 0;


    private int commentsCount = 0;

    private boolean isDeleted = false;


}