    package com.kamiloses.friendservice.websockets;

    import com.kamiloses.friendservice.dto.SendMessageDto;
    import com.kamiloses.friendservice.dto.UserDetailsDto;
    import com.kamiloses.friendservice.service.FriendshipService;
    import com.kamiloses.friendservice.service.RabbitFriendshipProducer;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Controller;

    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;
    import java.util.Set;

    @Controller
    public class WebSocketsController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitFriendshipProducer rabbitFriendshipProducer;
        public WebSocketsController(SimpMessagingTemplate messagingTemplate, RabbitFriendshipProducer rabbitFriendshipProducer) {
            this.messagingTemplate = messagingTemplate;
            this.rabbitFriendshipProducer = rabbitFriendshipProducer;
        }

        @MessageMapping("/chat.sendMessage")
        public void checkFriendsActivity(SendMessageDto message) {

            UserDetailsDto userDetailsDto = rabbitFriendshipProducer.askForUserDetails(message.getUsername());


            SendMessageDto sendMessageDto = new SendMessageDto(message.getChatId(),message.getMessage(), userDetailsDto.getUsername(), userDetailsDto.getFirstName(),userDetailsDto.getLastName());

            System.err.println("message odebrana :"+sendMessageDto);


            messagingTemplate.convertAndSend("/topic/public/"+message.getChatId(), sendMessageDto);

        }

    }
