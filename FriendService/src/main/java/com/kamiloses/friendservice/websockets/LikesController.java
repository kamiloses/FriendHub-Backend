package com.kamiloses.friendservice.websockets;

import com.kamiloses.friendservice.dto.LikesWebsocketsDto;
import com.kamiloses.friendservice.dto.SendMessageDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class LikesController {

    private final SimpMessagingTemplate messagingTemplate;

    public LikesController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/chat.likeAdd")
    public void changeNumberOfLikes(LikesWebsocketsDto likesWebsocketsDto) {

         likesWebsocketsDto.setNumberOfLikes(likesWebsocketsDto.getNumberOfLikes() + 1);
        messagingTemplate.convertAndSend("/topic/public/likes", likesWebsocketsDto );

    }


}
