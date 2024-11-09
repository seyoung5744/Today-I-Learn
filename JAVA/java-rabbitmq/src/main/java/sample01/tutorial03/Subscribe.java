package sample01.tutorial03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Subscribe {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        //exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //create queue
        String queueName = channel.queueDeclare().getQueue();
        //binding
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit)");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received : " + msg);
        };

        channel.basicConsume(queueName, true, callback, consumerTag -> { });
    }

}
