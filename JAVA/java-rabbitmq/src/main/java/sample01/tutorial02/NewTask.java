package sample01.tutorial02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private final static String QUEUE_NAME = "TASK_QUEUE";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try(Connection conn = factory.newConnection();
            Channel channel = conn.createChannel()){

            //durable
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // Tutorial 2.
            String msg = String.join(" ", args);

            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            System.out.println("[x] Sent : " + msg);
//            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
//            System.out.println("[x] Sent : " + msg);

        }
    }
}
