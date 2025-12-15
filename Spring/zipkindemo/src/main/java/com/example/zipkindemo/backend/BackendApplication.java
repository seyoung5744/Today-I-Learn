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
            long time = System.nanoTime();
//            if (time % 10 < 3) {
//                throw new RuntimeException("error");
//            }
            log.info("controller : {}", orderNumber);
            paymentService.payment(time, orderNumber * 10);
            return "OK " + orderNumber;
        }
    }

    @Slf4j
    @Service
    static class BackendPaymentService {

        @Autowired
        private Tracer tracer;

        public BackendPaymentService(Tracer tracer) {
            this.tracer = tracer;
        }

        @SneakyThrows
        public void payment(long time, Integer price) {
            Span span = tracer.nextSpan().name("backendPayment");
            try (Tracer.SpanInScope ws = tracer.withSpan(span.start())) {
                span.tag("payment-price", price);

                TimeUnit.MICROSECONDS.sleep(new Random().nextInt(500) + 100);
                log.info("{}: payment approved : {}", time, price);

            } finally {
                span.end();
            }
        }
    }
}
