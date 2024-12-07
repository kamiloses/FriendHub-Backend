package com.kamiloses.friendservice.websockets;

import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.service.RabbitFriendshipProducer;
import jakarta.annotation.PreDestroy;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventsUserAvailability {

    private final RedisTemplate<String, String> redisTemplate;

    public EventsUserAvailability(RedisTemplate<String, String> redisTemplate, RabbitFriendshipProducer rabbitFriendshipProducer) {
        this.redisTemplate = redisTemplate;
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
    }
     private final RabbitFriendshipProducer rabbitFriendshipProducer;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String regex = "username=\\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(headerAccessor.toString());

        if (matcher.find()) {
            String username = matcher.group(1);
            String sessionId = headerAccessor.getSessionId();


            //UserDetailsDto userDetails = rabbitFriendshipProducer.askForUserDetails(username);
            redisTemplate.opsForValue().set(sessionId,username);

            System.out.println("user: "+username +" Connected");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();


        String username = redisTemplate.opsForValue().get(sessionId);
        redisTemplate.delete(sessionId);

        System.out.println("user: "+username +" Disconnected");


    }

    @PreDestroy
    public void cleanUp(){



    }



}
