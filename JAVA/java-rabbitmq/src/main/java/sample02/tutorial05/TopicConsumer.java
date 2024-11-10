package sample02.tutorial05;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class TopicConsumer {

    private final static String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        // 1. ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2. setting Host
        factory.setHost("localhost");
        // 3. get Connection
        Connection conn = factory.newConnection();
        // 4. get Channel
        Channel channel = conn.createChannel();

        // validation
        if (args.length < 1) {
            System.out.println("It must take at least one Binding Key");
            System.exit(0);
        }

        // direct -> topic
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //create queue
        String queueName = channel.queueDeclare().getQueue();
        //binding
        for (String bindingKey : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println("[*] Waiting for messages. (Press CTRL + C to exit)");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received = " + delivery.getEnvelope().getRoutingKey() + "':'" + msg + "'");
        };

        boolean acknowledge = true;
        channel.basicConsume(queueName, acknowledge, callback, consumerTag -> { });
    }

}
