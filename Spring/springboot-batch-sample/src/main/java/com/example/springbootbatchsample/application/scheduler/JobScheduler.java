package com.example.springbootbatchsample.application.scheduler;

import com.example.springbootbatchsample.application.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
public class JobScheduler {

    private final JobService jobService;

    @Scheduled(fixedDelay = 5000)
    public void createArticleJob() throws Exception {
        jobService.runCreateArticleJob();
    }
}
