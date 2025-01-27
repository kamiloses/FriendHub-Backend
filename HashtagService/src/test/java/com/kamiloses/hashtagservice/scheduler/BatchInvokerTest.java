package com.kamiloses.hashtagservice.scheduler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;


//@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BatchInvokerTest {


    @MockitoSpyBean
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private BatchInvoker batchInvoker;


    @BeforeAll
    public void setUp() {
        // Saved 18 hashTags to database . 9 values are outdated so they will be deleted.


        redisTemplate.getConnectionFactory().getReactiveConnection().serverCommands().flushAll().subscribe();
        long currentTime = Instant.now().getEpochSecond();
        double time24HoursAgo = (double) Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
        redisTemplate.opsForZSet().add("hashtag:#nature", "1", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#nature", "2", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#nature", "3", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#nature", "4", time24HoursAgo).subscribe();


        redisTemplate.opsForZSet().add("hashtag:#weekend", "5", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "6", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "7", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "8", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "9", time24HoursAgo).subscribe();

        redisTemplate.opsForZSet().add("hashtag:#love", "10", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#love", "11", currentTime).subscribe();


        redisTemplate.opsForZSet().add("hashtag:#photography", "12", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#photography", "13", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#photography", "14", currentTime).subscribe();

        redisTemplate.opsForZSet().add("hashtag:#fashion", "15", time24HoursAgo).subscribe();

        redisTemplate.opsForZSet().add("hashtag:#art", "16", time24HoursAgo).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#art", "17", currentTime).subscribe();
        redisTemplate.opsForZSet().add("hashtag:#art", "18", currentTime).subscribe();

    }

    @Test
    public void a() throws InterruptedException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {


        StepVerifier.create(returnsFullAmountOfValues())
                .expectNext(18L)
                .verifyComplete();


        batchInvoker.removeOutdatedHashtags().subscribe();

        StepVerifier.create(returnsFullAmountOfValues())
                .expectNext(9L)
                .verifyComplete();




    }






    private Mono<Long> returnsFullAmountOfValues() {
        return redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands().scan()
                .flatMap(keyBytes -> {
                    String key = new String(keyBytes.array(), StandardCharsets.UTF_8);
                    return redisTemplate.opsForZSet().size(key);
                })
                .reduce(0L, Long::sum);
    }




//    private void returnsFullAmountOfValues() {
//        sizeOfElements=0;
//      redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands().scan()
//                .flatMap(keyBytes -> {
//                    String key = new String(keyBytes.array(), StandardCharsets.UTF_8);
//
//                    return redisTemplate.opsForZSet().size(key);
//                })
//                .subscribe(size -> {
//                    sizeOfElements += size;
//                    System.err.println(sizeOfElements);
//                });
//
//
//    }
}