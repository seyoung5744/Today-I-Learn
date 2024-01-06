package com.example.springbootrabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[X] Received : " + msg);
        };

        /*
        public abstract String basicConsume(String s,
                                    boolean b,
                                    com.rabbitmq.client.DeliverCallback deliverCallback,
                                    com.rabbitmq.client.CancelCallback cancelCallback)
        */
        channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {});
    }

}
