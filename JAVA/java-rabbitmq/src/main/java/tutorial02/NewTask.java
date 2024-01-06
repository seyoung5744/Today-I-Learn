package tutorial02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class NewTask {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try(Connection conn = factory.newConnection();
            Channel channel = conn.createChannel()){

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Tutorial 2.
            String msg = String.join(" ", args);

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("[x] Sent : " + msg);

        }
    }
}
