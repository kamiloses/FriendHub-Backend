package com.kamiloses.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class PostEntity {

    @Id
    private String id;

    private String userId;

    private String content;

    private LocalDateTime createdAt;

    private int likeCount = 0;

    private int retweetCount = 0;

    private int commentsCount = 0;

    private boolean isDeleted = false;


}