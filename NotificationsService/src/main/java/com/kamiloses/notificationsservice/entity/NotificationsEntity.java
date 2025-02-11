package com.kamiloses.notificationsservice.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@Document
public class NotificationsEntity {

   @Id
   private String id;

    private String  recipientId;

    private String senderId;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String message;

    private boolean isRead = false;

    private Date createdAt;





}
