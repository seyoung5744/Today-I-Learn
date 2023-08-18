package com.example.ioexample.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = {"server.port=8080"})
public class IOTest {

    private static final String THREE_SECOND_URL = "http://localhost:8080/3second";
    private static final int LOOP_COUNT = 100;

    private final WebClient webClient = WebClient.create();
    private final CountDownLatch count = new CountDownLatch(LOOP_COUNT);

    @BeforeEach
    public void setup() {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
    }

    @Test
    public void blocking () {
        final RestTemplate restTemplate = new RestTemplate();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < 3; i++) {
            final ResponseEntity<String> response =
                restTemplate.exchange(THREE_SECOND_URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);
            assertThat(response.getBody()).contains("success");
        }

        stopWatch.stop();

        System.out.println(stopWatch.getTotalTimeSeconds());
    }
    
    @Test
    public void nonBlocking3 () throws InterruptedException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        for (int i = 0; i < LOOP_COUNT; i++) {
            this.webClient
                .get()
                .uri(THREE_SECOND_URL)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(it -> {
                    count.countDown();
                    System.out.println(it);
                });
        }

        count.await(10, TimeUnit.SECONDS);
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeSeconds());
    }
}
