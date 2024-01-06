package tutorial04;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class SubscribeSeverity {

    private final static String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{

        // 1. ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2. setting Host
        factory.setHost("localhost");
        // 3. get Connection
        Connection conn = factory.newConnection();
        // 4. get Channel
        Channel channel = conn.createChannel();

        // validation
        if(args.length < 1) {
            System.out.println("It must take at least one Severity[info/warning/error]");
            System.exit(0);
        }

        // exchange -> direct
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //create queue
        String queueName = channel.queueDeclare().getQueue();
        //binding
        for(String severity : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }

        System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit)");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received = " + delivery.getEnvelope().getRoutingKey() + "':'" + msg + "'");
        };

        boolean acknowledge = true;
        channel.basicConsume(queueName, acknowledge, callback, consumerTag -> { });
    }

}
