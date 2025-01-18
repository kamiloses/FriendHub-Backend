package com.kamiloses.hashtagservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Init {
    private RedisTemplate<String, String> redisTemplate;
    private TreeMap<Long,String> mostPopularHashtags = new TreeMap<>();
    public Init(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void  a() {



        Cursor<byte[]> allKeys = redisTemplate.getConnectionFactory().getConnection().scan(ScanOptions.scanOptions().match("hashtag:*").build());

        while (allKeys.hasNext()) {
            byte[] byteKey = allKeys.next();
            String key = new String(byteKey, StandardCharsets.UTF_8);
            long oldestAllowedTime = Instant.now().getEpochSecond() - (1 * 15);
            redisTemplate.opsForZSet().removeRangeByScore(key, 0,oldestAllowedTime );
            Long count = redisTemplate.opsForZSet().size(key);



            mostPopularHashtags.put(count, key);

    }

        Map<Long, String> topThreeHashtags = mostPopularHashtags.entrySet()
                .stream()
                .limit(3)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

}
