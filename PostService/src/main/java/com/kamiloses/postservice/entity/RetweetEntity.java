package com.kamiloses.postservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Document
public class RetweetEntity {

    @Id
    private String id;

    private String retweetedByUserId;

    private String originalPostId;




}
