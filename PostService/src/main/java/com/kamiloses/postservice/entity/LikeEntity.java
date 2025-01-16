package com.kamiloses.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document @Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class LikeEntity {



    @Id
    private String id;

    private String likedByUserId;

    private String originalPostId;
}
