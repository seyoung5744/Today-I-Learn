package com.example.zipkindemo.backend;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@EnableKafka
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

        @Autowired
        private KafkaTemplate<String, String> kafkaTemplate;

        public BackendPaymentService(Tracer tracer) {
            this.tracer = tracer;
        }

        @SneakyThrows
        public void payment(Integer price) {
//            Span parent = tracer.nextSpan().name("backendPayment");
//            try (Tracer.SpanInScope ws = tracer.withSpan(parent.start())) {
                kafkaTemplate.send("backend", price + "원이 결제 요청됩니다.");

//                log.info("approved");
//            } finally {
//                parent.end();
//            }
        }

        @KafkaListener(topics = "backend", groupId = "backend-c1")
        public void consume(ConsumerRecord<String, String> record) {
            log.info("consume : {} 그리고 결제 완료!", record.value());
        }
    }
}
