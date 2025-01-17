package com.kamiloses.hashtagservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class RabbitHashtagListener {


    private RedisTemplate<String, String> redisTemplate;

    private Long id=0L;
    private String redisKey="hashtag:";

    public RabbitHashtagListener(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @RabbitListener()
    public void receiveHashtagsFromPostAndAddToRedis(String hashtags) throws JsonProcessingException {

       List.of("#LOL","#LOL","#HAHA").stream().map(hashtag-> {
                     id++;
                     redisKey+=id;
                   redisTemplate.opsForValue().set(redisKey, hashtag);
                    redisTemplate.expire(redisKey, Duration.ofHours(24));

       return hashtag;}
       );

    }
}