package com.example.springbootrabbitmq;

import java.util.concurrent.TimeUnit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(RabbitTemplate rabbitTemplate, Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        String message = "Hello from RabbitMQ!!!";
        rabbitTemplate.convertAndSend(SpringbootRabbitmqApplication.topicExchangeName, "foo.bar.baz", message);
        receiver.getLatch().await(10_000, TimeUnit.MICROSECONDS);
    }
}
