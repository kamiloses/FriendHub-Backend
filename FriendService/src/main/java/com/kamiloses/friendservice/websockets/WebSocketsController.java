    package com.kamiloses.friendservice.websockets;

    import com.kamiloses.friendservice.dto.UserDetailsDto;
    import com.kamiloses.friendservice.service.FriendshipService;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Controller;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Set;

    @Controller
    public class WebSocketsController {

    

        @MessageMapping("/chat.sendMessage")
        public void checkFriendsActivity(String a) {

            System.err.println("message odebrana :"+a);

            //messagingTemplate.convertAndSend("/topic/public/friendsOnline", message);

        }

    }
