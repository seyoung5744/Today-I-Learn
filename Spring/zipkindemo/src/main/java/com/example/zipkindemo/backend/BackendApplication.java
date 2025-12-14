package com.example.zipkindemo.backend;

import io.micrometer.tracing.annotation.NewSpan;
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
                "--spring.application.name=backend",
                "--server.port=8081");
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

        @SneakyThrows
        @NewSpan
        public void payment(Integer price) {
            TimeUnit.MICROSECONDS.sleep(new Random().nextInt(500) + 100);
            log.info("payment approved : {}", price);
        }
    }
}
