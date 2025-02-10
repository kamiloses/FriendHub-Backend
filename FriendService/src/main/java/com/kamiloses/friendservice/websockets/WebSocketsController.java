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
        //todo popraw potem ten/topic/public na queue i po stronie frontendu
        @MessageMapping("/chat.sendMessage")
        public void checkFriendsActivity(SendMessageDto message) {

            UserDetailsDto userDetailsDto = rabbitFriendsProducer.askForUserDetails(message.getUsername()).block();


            SendMessageDto sendMessageDto = new SendMessageDto(message.getChatId(), message.getMessage(), userDetailsDto.getUsername(), userDetailsDto.getFirstName(), userDetailsDto.getLastName());


            messagingTemplate.convertAndSend("/topic/public/" + message.getChatId(), sendMessageDto);

        }

    }






