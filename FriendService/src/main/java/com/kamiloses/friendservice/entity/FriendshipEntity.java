package com.kamiloses.friendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipEntity {

    @Id
    private String id;

    private String userId;
    private String friendId;
    private FriendshipStatus status;

    private Date createdAt;



}
