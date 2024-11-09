package sample01.tutorial06;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] args) throws Exception {

        // 1. ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 2. setting Host
        factory.setHost("localhost");
        // 3. get Connection
        Connection conn = factory.newConnection();
        // 4. get Channel
        Channel channel = conn.createChannel();

        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queuePurge(RPC_QUEUE_NAME);

        int prefetchCount = 1;

        channel.basicQos(prefetchCount);

        System.out.println(" [x] Awaiting RPC requests");

        Object monitor = new Object();

        DeliverCallback callback = (consumerTag, delivery) -> {
            AMQP.BasicProperties props = new AMQP.BasicProperties
                                        .Builder()
                                        .correlationId(delivery.getProperties().getCorrelationId())
                                        .build();

            String response = "";

            try {
                String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
                int n = Integer.parseInt(msg);

                System.out.println(" [.] fib(" + msg + ")");
                response += fib(n);
            } catch (RuntimeException e) {
                System.out.println(" [.] " + e.toString());
            } finally {
                channel.basicPublish("", delivery.getProperties().getReplyTo(), props, response.getBytes(StandardCharsets.UTF_8));
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                synchronized (monitor) {
                    monitor.notify();
                }
            }
        };

        channel.basicConsume(RPC_QUEUE_NAME, false, callback, (consumerTag -> { }));
        // Wait and be prepared to consume the message from RPC client.
        while (true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
