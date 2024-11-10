package sample02.tutorial03;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Publish {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");

        try(Connection conn = factory.newConnection();
            Channel channel = conn.createChannel()){

            String queueName = channel.queueDeclare().getQueue(); //random Queue

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //exchange

            channel.queueBind(queueName, EXCHANGE_NAME, ""); //binding

            String msg = args.length < 1? "info: Hello World!" : String.join(" ", args);

            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
            System.out.println("[x] Sent : " + msg);
        }
    }
}
