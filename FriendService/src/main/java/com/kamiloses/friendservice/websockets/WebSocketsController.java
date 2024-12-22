    package com.kamiloses.friendservice.websockets;

    import com.kamiloses.friendservice.dto.SendMessageDto;
    import com.kamiloses.friendservice.dto.UserDetailsDto;
    import com.kamiloses.friendservice.service.RabbitFriendsProducer;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Controller;


    @Controller
    public class WebSocketsController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitFriendsProducer rabbitFriendsProducer;
        public WebSocketsController(SimpMessagingTemplate messagingTemplate, RabbitFriendsProducer rabbitFriendsProducer) {
            this.messagingTemplate = messagingTemplate;
            this.rabbitFriendsProducer = rabbitFriendsProducer;
        }

        @MessageMapping("/chat.sendMessage")
        public void checkFriendsActivity(SendMessageDto message) {

            UserDetailsDto userDetailsDto = rabbitFriendsProducer.askForUserDetails(message.getUsername());


            SendMessageDto sendMessageDto = new SendMessageDto(message.getChatId(),message.getMessage(), userDetailsDto.getUsername(), userDetailsDto.getFirstName(),userDetailsDto.getLastName());

  //todo popraw potem ten/topic/public na queue czy jakoś tak i po stronie frontendu tez
            // i popraw reaktywnosc przy rabbicie

            messagingTemplate.convertAndSend("/topic/public/"+message.getChatId(), sendMessageDto);

        }

    }
