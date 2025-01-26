package com.kamiloses.hashtagservice.service;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.transaction.PlatformTransactionManager;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

@Configuration
public class BatchConfig {

    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(ReactiveRedisTemplate<String, String> redisTemplate, JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.redisTemplate = redisTemplate;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public ItemReader<String> getAllKeys() {

        return new IteratorItemReader<>(
                redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
                        .scan(ScanOptions.scanOptions().match("hashtag:*").build())
                        .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString()).toIterable());
    }


    @Bean
    public ItemWriter<String> removeKeysOlderThan24Hours() {
        long oldestAllowedTime = Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
        Range<Double> range = Range.closed(0.0, (double) oldestAllowedTime);

        return keys -> {
            for (String key : keys) {
                redisTemplate.opsForZSet().removeRangeByScore(key, range).block();
            }

        };

    }

}


//public Mono<Void> removeOutdatedHashtags () {
//    return redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
//            .scan(ScanOptions.scanOptions().match("hashtag:*").build())
//            .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString())
//            .flatMap(key -> {
//                long oldestAllowedTime = Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
//                Range<Double> range = Range.closed(0.0, (double) oldestAllowedTime);
//                return redisTemplate.opsForZSet().removeRangeByScore(key, range);
//            }).then();
//
//}