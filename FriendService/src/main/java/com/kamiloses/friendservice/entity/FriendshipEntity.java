package com.kamiloses.friendservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipEntity {

    @Id
    private String id;

    private String userId;
    private String friendId;

    private Date createdAt;
    private Status status;

    private List<String> messagesId;


}
