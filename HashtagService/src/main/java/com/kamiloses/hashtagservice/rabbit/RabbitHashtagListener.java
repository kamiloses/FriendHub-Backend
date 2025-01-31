package com.kamiloses.hashtagservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class RabbitHashtagListener {


    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public RabbitHashtagListener(ReactiveRedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @RabbitListener
    public void receiveHashtagsFromPostAndAddToRedis(String hashtagsJson) throws JsonProcessingException {

        List<String> hashtags = objectMapper.readValue(hashtagsJson, new TypeReference<>() {});

        hashtags.forEach(hashtag -> {
            long currentTime = Instant.now().getEpochSecond();
            String id = UUID.randomUUID().toString();


            redisTemplate.opsForZSet().add("hashtag:" + hashtag, id, currentTime).subscribe();
        });
    }


}