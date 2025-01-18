package com.kamiloses.hashtagservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.hashtagservice.rabbit.RabbitHashtagListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HashtagServiceTest {


    @Autowired
    private RabbitHashtagListener rabbitHashtagListener;

    @Autowired
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private HashtagService hashtagService;


    private String formattedListOfHashtags() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // nature - 7 / love - 5 / weekend - 5 / instagram - 4 / fashion - 2 / photography - 2 / art - 1
        return objectMapper.writeValueAsString(List.of("#love", "#love", "#love", "#love", "#love", "#instagram", "#instagram", "#instagram", "#instagram", "#fashion", "#fashion", "#nature", "#nature", "#nature", "#nature", "#nature", "#nature", "#nature", "#weekend", "#weekend", "#weekend", "#weekend", "#weekend", "#photography", "#photography", "#art"));

    }

    @BeforeAll
    public void removeAllRedisElements() throws JsonProcessingException {
        redisTemplate.getConnectionFactory().getReactiveConnection().serverCommands().flushAll().subscribe();
        rabbitHashtagListener.receiveHashtagsFromPostAndAddToRedis(formattedListOfHashtags());
    }


    @Test
    public void should_save_hashtags_to_redis() {

        System.err.println(        hashtagService.getMostPopularHashtags().block());


    }


}