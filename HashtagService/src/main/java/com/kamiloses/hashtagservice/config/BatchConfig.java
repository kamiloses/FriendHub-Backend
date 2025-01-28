package com.kamiloses.hashtagservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.transaction.PlatformTransactionManager;
import reactor.core.publisher.Flux;


import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

@Configuration
@Slf4j
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

        Flux<String> keysFlux = redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
                .scan(ScanOptions.scanOptions().match("hashtag:*").build())
                .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString());

        return new IteratorItemReader<>(keysFlux.collectList().block());
    }




    @Bean
    public ItemWriter<String> removeKeysOlderThan24Hours() {
        double time24HoursAgo =(double) Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
        double time1YearAgo =(double) Instant.now().minus(Duration.ofDays(365)).toEpochMilli();
        Range<Double> range = Range.closed(time1YearAgo, time24HoursAgo);

        return keys -> {
            for (String key : keys) {
                redisTemplate.opsForZSet().removeRangeByScore(key, range)
                        .doOnSuccess(removed -> log.info("Removed {} items from {}", removed, key))
                        .block();

            }

        };

    }


    @Bean
    public Step processHashtagStep(ItemReader<String> reader, ItemWriter<String> writer) {


        return new StepBuilder("processHashtagStep", jobRepository)
                .<String, String>chunk(1000, transactionManager)
                .reader(reader)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public Job processHashtagJob(Step processOderStep){
        return new JobBuilder("hashtagProcessingJob",jobRepository)
                .start(processOderStep)
                .build();


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