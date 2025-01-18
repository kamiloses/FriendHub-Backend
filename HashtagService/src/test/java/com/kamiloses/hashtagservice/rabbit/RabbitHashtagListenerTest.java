package com.kamiloses.hashtagservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RabbitHashtagListenerTest {
    @Autowired
    private  RabbitHashtagListener rabbitHashtagListener;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private String formattedListOfHashtags() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // nature - 7 / love - 5 / weekend - 5 / instagram - 4 / fashion - 2 / photography - 2 / art - 1
        return objectMapper.writeValueAsString(List.of("#love", "#love", "#love", "#love", "#love", "#instagram", "#instagram", "#instagram", "#instagram", "#fashion", "#fashion", "#nature", "#nature", "#nature", "#nature", "#nature", "#nature", "#nature", "#weekend", "#weekend", "#weekend", "#weekend", "#weekend", "#photography", "#photography", "#art"));

    }
    @BeforeAll
    public void removeAllRedisElements(){
        redisTemplate.getConnectionFactory().getConnection().flushDb();


    }


    @Test
    public void should_save_hashtags_to_redis() throws JsonProcessingException {
        rabbitHashtagListener.receiveHashtagsFromPostAndAddToRedis(formattedListOfHashtags());



    }


}