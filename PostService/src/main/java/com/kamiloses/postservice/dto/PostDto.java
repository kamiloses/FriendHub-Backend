package com.kamiloses.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data @AllArgsConstructor @NoArgsConstructor

public class PostDto {

    //todo NArazie tak samo dto wygląda jak postEntity. potem zmienić
    private String id;

    private UserDetailsDto user;

    private String content;

    private LocalDateTime createdAt;

    private int likeCount = 0;

    private int retweetCount = 0;

    private int commentCount = 0;



    private boolean isDeleted = false;

}
