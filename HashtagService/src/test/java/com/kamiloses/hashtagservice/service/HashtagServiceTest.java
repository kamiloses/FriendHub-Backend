package com.kamiloses.hashtagservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.hashtagservice.rabbit.RabbitHashtagListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public void should_receivedMostPopularHashtags() {
        HashMap<String, Long> expectedHashtags = new HashMap<>();
        expectedHashtags.put("hashtag:#nature", 7L);
        expectedHashtags.put("hashtag:#love", 5L);
        expectedHashtags.put("hashtag:#weekend", 5L);
        expectedHashtags.put("hashtag:#instagram", 4L);
        expectedHashtags.put("hashtag:#fashion", 2L);


        System.out.println(hashtagService.getMostPopularHashtags().block());

        StepVerifier.create(hashtagService.getMostPopularHashtags())
                .expectNext(expectedHashtags)
                .verifyComplete();
    }
}