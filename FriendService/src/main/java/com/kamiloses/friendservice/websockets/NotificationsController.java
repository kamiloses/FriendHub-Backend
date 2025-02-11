package com.kamiloses.friendservice.websockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationsController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    //this method will display in real time all notifications about users trying to invite me as friends.
    @MessageMapping("/chat.friendInvitation")
    public void FriendInvitationController() {
        String username="kamiloses";

        System.err.println("WYŚWIETLA");

      //  messagingTemplate.convertAndSend("/topic/public/user/"+username+"/notifications", sendMessageDto);


    }








}
