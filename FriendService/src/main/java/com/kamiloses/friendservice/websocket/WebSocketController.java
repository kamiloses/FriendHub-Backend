package com.kamiloses.friendservice.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE})

public class WebSocketController {

    @MessageMapping("/chat.requestActiveUsers")
    public void handleRequestActiveUsers() {

        System.err.println("DZIAŁA");
     //   messagingTemplate.convertAndSend("/topic/public/ActiveUsersAtStart", allActiveUsers);

    }


}
