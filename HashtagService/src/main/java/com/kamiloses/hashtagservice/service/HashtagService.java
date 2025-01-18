package com.kamiloses.hashtagservice.service;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HashtagService {

    private RedisTemplate<String, String> redisTemplate;

    private HashMap<String, Long> mostPopularHashtags;

    public HashtagService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }




    public HashMap<String, Long> getMostPopularHashtags() {
        mostPopularHashtags = new HashMap<>();

        //returning all redis keys as byte[]
        Cursor<byte[]> allKeys = redisTemplate.getConnectionFactory().getConnection().scan(ScanOptions.scanOptions().match("hashtag:*").build());

        while (allKeys.hasNext()) {
            byte[] byteKey = allKeys.next();
            String key = new String(byteKey, StandardCharsets.UTF_8);

            //returning amount of values related with specific key
            Long count = redisTemplate.opsForZSet().size(key);
            mostPopularHashtags.put(key, count);
        }



        //sorting on top 5 most popular hashtags
        return mostPopularHashtags.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new));
    }

}







