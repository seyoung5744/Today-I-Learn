package sample01.tutorial05;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

public class TopicProducer {

    private final static String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {

        // 1. create Factory
        ConnectionFactory factory = new ConnectionFactory();
        // 2. set Host
        factory.setHost("localhost");

        try (
            // 3. get Connection
            Connection conn = factory.newConnection();
            // 4. get Channel
            Channel channel = conn.createChannel()) {

            // Exchange Declare
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            String routingKey = getRoutingKey(args);
            String message = getMessage(args);
            
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("[x] Sent = [" + routingKey + "] : " + message);
        }
    }

    private static String getRoutingKey(String[] strings) {
        return strings.length < 1 ? "anonymous.info" : strings[0];
    }

    private static String getMessage(String[] args) {
        return args.length < 2? "Hello World" : joinStrings(args, " ", 1);
    }

    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if(length == 0) return "";
        if(length <= startIndex) return "";
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }

        return words.toString();
    }
}
