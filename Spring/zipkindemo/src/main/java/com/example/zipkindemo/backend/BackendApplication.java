package com.example.zipkindemo.backend;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class,
                "--spring.profiles.active=back");
    }

    @Slf4j
    @RestController
    static class BackendController {

        @Autowired
        private BackendPaymentService paymentService;

        @GetMapping("/order/{orderNumber}")
        public String order(@PathVariable Integer orderNumber) {
            log.info("controller : {}", orderNumber);
            paymentService.payment(orderNumber * 10);
            return "OK " + orderNumber;
        }
    }

    @Slf4j
    @Service
    static class BackendPaymentService {

        @Autowired
        private Tracer tracer;

        private ExecutorService executorService = Executors.newFixedThreadPool(10);

        public BackendPaymentService(Tracer tracer) {
            this.tracer = tracer;
        }

        @SneakyThrows
        public void payment(Integer price) {
            Span parent = tracer.nextSpan().name("backendPayment");
            try (Tracer.SpanInScope ws = tracer.withSpan(parent.start())) {
                parent.tag("payment-price", price);

                executorService.submit(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        Span span2 = tracer.nextSpan(parent).name("runnable");
                        try (Tracer.SpanInScope ws = tracer.withSpan(span2.start())) {
                            TimeUnit.MICROSECONDS.sleep(new Random().nextInt(500) + 100);
                            log.info("async approve : {}", price);
                        } finally {
                            span2.end();
                        }
                    }
                });
                log.info("approved");
            } finally {
                parent.end();
            }
        }
    }
}
