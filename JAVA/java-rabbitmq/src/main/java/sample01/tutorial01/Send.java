package sample01.tutorial01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try (Connection conn = factory.newConnection();
            Channel channel = conn.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "HELLO WORLD!!!";

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("[x] Sent : " + msg);
        }
    }
}
