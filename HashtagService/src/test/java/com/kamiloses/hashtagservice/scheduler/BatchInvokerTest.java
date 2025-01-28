package com.kamiloses.hashtagservice.scheduler;

import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class BatchInvokerTest {


    @MockitoSpyBean
    private ReactiveRedisTemplate<String, String> redisTemplate;

    @Autowired
    private BatchInvoker batchInvoker;


    @BeforeAll
    public void setUp() {
        // Saved 19 hashTags to database . 10 values are outdated so they will be deleted.

        redisTemplate.getConnectionFactory().getReactiveConnection().serverCommands().flushAll().subscribe();
        long currentTime = Instant.now().getEpochSecond();
        double time25HoursAgo = (double) Instant.now().minus(Duration.ofHours(25)).toEpochMilli();
        redisTemplate.opsForZSet().add("hashtag:#nature", "1", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#nature", "2", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#nature", "3", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#nature", "4", time25HoursAgo).block();


        redisTemplate.opsForZSet().add("hashtag:#weekend", "5", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "6", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "7", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "8", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#weekend", "9", time25HoursAgo).block();

        redisTemplate.opsForZSet().add("hashtag:#love", "10", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#love", "11", currentTime).block();


        redisTemplate.opsForZSet().add("hashtag:#photography", "12", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#photography", "13", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#photography", "14", currentTime).block();

        redisTemplate.opsForZSet().add("hashtag:#fashion", "15", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#fashion", "16", time25HoursAgo).block();

        redisTemplate.opsForZSet().add("hashtag:#art", "17", time25HoursAgo).block();
        redisTemplate.opsForZSet().add("hashtag:#art", "18", currentTime).block();
        redisTemplate.opsForZSet().add("hashtag:#art", "19", currentTime).block();

    }

    @Test
    @Order(1)
    public void should_Check_If_All_Hashtags_Have_Been_Saved_To_Db()  {

        StepVerifier.create(returnsFullAmountOfValues())
                .expectNext(19L)
                .verifyComplete();




    }

    @Test
    @Order(2)
    public void Should_Remove10_Outdated_Hashtags() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // Data that is supposed to be saved to Redis before invoking the tests but  is not always saved in time,
        // so the test may need to be run a second time to ensure the data is properly saved before execution.

        batchInvoker.runJob();

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













}