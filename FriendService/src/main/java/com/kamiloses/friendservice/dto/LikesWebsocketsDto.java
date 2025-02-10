package com.kamiloses.friendservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class LikesWebsocketsDto {


    private String username;
    private String postId;
    private boolean wasLiked;
    private int numberOfLikes;
}
