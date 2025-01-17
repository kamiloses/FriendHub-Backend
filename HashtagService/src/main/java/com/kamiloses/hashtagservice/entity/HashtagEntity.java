package com.kamiloses.hashtagservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document @Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HashtagEntity {



    @Id
    private Long id;

    private String name;

    private Long usageCount = 0L;

    private Date lastUsed;

}
