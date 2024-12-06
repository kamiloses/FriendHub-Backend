package com.kamiloses.userservice.websockets;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
@Component
public class EventsUserAvailability {


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        System.err.println("DZIAŁA");

//        UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
//        messagingTemplate.convertAndSend("/topic/public", new AvailableUserService(userEntity.getId(), "CONNECTED"));
//
//        availableUserService.userConnected(userEntity.getId());



    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.err.println("NIE DZIAŁA");
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String loggedUser = headerAccessor.getUser().getName();
//        UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
//        messagingTemplate.convertAndSend("/topic/public", new AvailableUserService(userEntity.getId(), "DISCONNECTED"));
//
//
//        log.error(userEntity.getUserDetailsEntity().getFirstName() + " " + userEntity.getUserDetailsEntity().getLastName() + " has been disconnected");
//        availableUserService.userDisconnected(userEntity.getId());
//
//
//        userEntity.setLastActivity(new Date());
//        userRepository.save(userEntity);

    }


}
