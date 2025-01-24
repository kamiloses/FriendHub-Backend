package com.kamiloses.friendservice.websockets;

import com.kamiloses.friendservice.dto.UserActivityDto;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component  @Slf4j
public class EventsUserAvailability {

    //todo reaktywny redis

    private final RedisTemplate<String, String> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;
    private final   Gauge gauge;
    private int onlineUsersCount = 0;

    public EventsUserAvailability(RedisTemplate<String, String> redisTemplate, SimpMessagingTemplate messagingTemplate, MeterRegistry meterRegistry) {
        this.redisTemplate = redisTemplate;
        this.messagingTemplate = messagingTemplate;
         gauge = Gauge.builder("active_users_online", this, instance -> this.onlineUsersCount)
                .description("Number of users currently online")
                .register(meterRegistry);

    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String regex = "username=\\[(.*?)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(headerAccessor.toString());

        if (matcher.find()) {
            String username = matcher.group(1);
            String sessionId = headerAccessor.getSessionId();


            redisTemplate.opsForValue().set(sessionId, username);

            log.info("user: {} Connected", username);
            updateFriendStatus(username, true);
            onlineUsersCount++;
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();


        String username = redisTemplate.opsForValue().get(sessionId);

        deleteByValue(username);


        log.info("user: {} disconnected", username);

        updateFriendStatus(username, false);
        onlineUsersCount--;


    }


    private void updateFriendStatus(String username, boolean isOnline) {
        UserActivityDto friendStatus = new UserActivityDto(username, isOnline);
        messagingTemplate.convertAndSend("/topic/public/friendsOnline", friendStatus);
    }


    private void deleteByValue(Object valueToDelete) {
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                Object value = redisTemplate.opsForValue().get(key);
                if (value != null && value.equals(valueToDelete)) {
                    redisTemplate.delete(key);
                }
            }

        }
    }










    @PreDestroy
    public void cleanUpTheRedisDB() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();

    }



}