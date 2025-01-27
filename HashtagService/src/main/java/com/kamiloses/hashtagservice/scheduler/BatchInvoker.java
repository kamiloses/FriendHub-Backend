package com.kamiloses.hashtagservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class BatchInvoker {
    private final JobLauncher jobLauncher;
    private final Job processHashtagJob;

    public BatchInvoker(JobLauncher jobLauncher, Job processHashtagJob, ReactiveRedisTemplate<String, String> redisTemplate) {
        this.jobLauncher = jobLauncher;
        this.processHashtagJob = processHashtagJob;
        this.redisTemplate = redisTemplate;
    }

    //  @Scheduled(fixedRate = 10000)
    //@Scheduled(cron ="0 */5 * ? * *")
   public void runJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
 //      jobLauncher.run(processHashtagJob, new JobParameters());
        log.warn("Outdated hashtags have been removed from the database.");



   }


   //todo usuń
    private final ReactiveRedisTemplate<String, String> redisTemplate;

public Mono<Void> removeOutdatedHashtags () {

    double time24HoursAgo =(double) Instant.now().minus(Duration.ofHours(24)).toEpochMilli();
    double time1YearAgo =(double) Instant.now().minus(Duration.ofDays(365)).toEpochMilli();
    return redisTemplate.getConnectionFactory().getReactiveConnection().keyCommands()
            .scan(ScanOptions.scanOptions().match("hashtag:*").build())
            .map(byteBuffer -> StandardCharsets.UTF_8.decode(byteBuffer).toString())
            .flatMap(key -> {

                Range<Double> range = Range.closed(time1YearAgo, time24HoursAgo);
                return redisTemplate.opsForZSet().removeRangeByScore(key, range)
                        .doOnSuccess(removed -> log.info("Removed {} items from {}", removed, key));
            }).then();

}


}
