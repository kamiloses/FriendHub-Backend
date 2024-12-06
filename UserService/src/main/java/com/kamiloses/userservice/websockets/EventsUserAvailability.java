package com.kamiloses.userservice.websockets;

import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EventsUserAvailability {

    private final ConcurrentHashMap<String, String> activeUsers = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String regex = "username=\\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(headerAccessor.toString());

        if (matcher.find()) {
            String username = matcher.group(1);
            String sessionId = headerAccessor.getSessionId();

            activeUsers.put(sessionId, username);

            System.out.println("user: "+username +" Connected");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        String username = activeUsers.remove(sessionId);

        System.out.println("user: "+username +" Disconnected");


    }


}
