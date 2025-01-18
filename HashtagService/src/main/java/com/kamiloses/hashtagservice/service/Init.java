package com.kamiloses.hashtagservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Component
public class Init {
    private RedisTemplate<String, String> redisTemplate;
    private HashSet<String> setOfKeys=new HashSet<>();

    public Init(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void  a() {
        List.of("#LOL", "#LOL", "#HAHA", "#HAHA", "#HAHA", "#XD").forEach(hashtag -> {
            long currentTime = Instant.now().getEpochSecond();
            String id = UUID.randomUUID().toString();

            //key             value            score
            redisTemplate.opsForZSet().add("hashtag:" + hashtag, id, currentTime);
        });


        Cursor<byte[]> allKeys = redisTemplate.getConnectionFactory().getConnection().scan(ScanOptions.scanOptions().match("hashtag:*").build());

        while (allKeys.hasNext()) {
            byte[] byteKey = allKeys.next();
            String key = new String(byteKey, StandardCharsets.UTF_8);
             setOfKeys.add(key);
            long oldestAllowedTime = Instant.now().getEpochSecond() - (1 * 60);
        redisTemplate.opsForZSet().removeRangeByScore(key, 0,oldestAllowedTime );
    }}}
