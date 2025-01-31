package com.kamiloses.hashtagservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class BatchInvoker {
    private final JobLauncher jobLauncher;
    private final Job processHashtagJob;

    public BatchInvoker(JobLauncher jobLauncher, Job processHashtagJob) {
        this.jobLauncher = jobLauncher;
        this.processHashtagJob = processHashtagJob;
    }

    @Scheduled(cron ="0 */5 * ? * *")
   public void runJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
       jobLauncher.run(processHashtagJob, new JobParameters());
        log.warn("Outdated hashtags have been removed from the database.");



   }


}
