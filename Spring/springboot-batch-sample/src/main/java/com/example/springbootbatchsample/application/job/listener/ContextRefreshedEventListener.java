package com.example.springbootbatchsample.application.job.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final JobExplorer jobExplorer;
    private final JobRepository jobRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Stop running jobs.");
        for (String jobName : jobExplorer.getJobNames()) {
            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(jobName);

            for (JobExecution jobExecution : runningJobExecutions) {
                jobExecution.setStatus(BatchStatus.STOPPED);
                jobExecution.setEndTime(LocalDateTime.now());
                for (StepExecution stepExecution : jobExecution.getStepExecutions()) {
                    if (stepExecution.getStatus().isRunning()) {
                        stepExecution.setStatus(BatchStatus.STOPPED);
                        stepExecution.setEndTime(LocalDateTime.now());
                        jobRepository.update(stepExecution);
                    }
                }
                jobRepository.update(jobExecution);
                log.info("Updated job execution status: {}", jobExecution.getJobId());
            }
        }
        log.info("Stopped running jobs.");
    }
}
