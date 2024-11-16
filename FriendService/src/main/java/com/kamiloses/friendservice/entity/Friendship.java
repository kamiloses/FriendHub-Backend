package com.kamiloses.friendservice.entity;

import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

public class Friendship {

    @Id
    private String id;

    private String userId;
    private String friendId;

    private LocalDateTime createdAt;
    private FriendshipStatus status;



    public enum FriendshipStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
