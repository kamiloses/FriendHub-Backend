package com.kamiloses.hashtagservice.service;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.ReactiveRedisConnection;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;




@Service
public class HashtagService {

    private final ReactiveRedisTemplate<String, String> redisTemplate;

    private HashMap<String, Long> mostPopularHashtags;


    public HashtagService(ReactiveRedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<HashMap<String, Long>> getMostPopularHashtags() {
        mostPopularHashtags = new HashMap<>();


        return redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
                .scan(ScanOptions.scanOptions().match("hashtag:*").build())//returning all redis keys as bytes
                .flatMap(keyBytes -> {
                    String key = new String(keyBytes.array(), StandardCharsets.UTF_8);
                    return redisTemplate.opsForZSet()//returning amount of values related with specific key
                            .size(key)
                            .map(size -> {
                                mostPopularHashtags.put(key, size);
                                return mostPopularHashtags;
                            });
                }).then(Mono.defer(() ->                //non-static method
                        Mono.just(
                                mostPopularHashtags.entrySet().stream()
                                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                                        .limit(5)
                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                (e1, e2) -> e1, LinkedHashMap::new)))));}




  public Mono<Void> removeOutdatedHashtags(){
      return redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
              .scan(ScanOptions.scanOptions().match("hashtag:*").build())
              .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString())
              .flatMap(key -> {
                  long oldestAllowedTime =  Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
                  Range<Double> range = Range.closed(0.0, (double) oldestAllowedTime);
                  return redisTemplate.opsForZSet().removeRangeByScore(key, range);}).then();

  }





}
