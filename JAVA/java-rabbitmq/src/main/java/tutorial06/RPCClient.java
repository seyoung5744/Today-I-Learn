package tutorial06;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class RPCClient implements AutoCloseable {

    private Connection conn;
    private Channel channel;

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        conn = factory.newConnection();
        channel = conn.createChannel();
    }

    public static void main(String[] args) {

        //AutoCloseable을 구현 -> try(...)에 선언 가능
        try (RPCClient fibonacciRPC = new RPCClient()) {

            IntStream.range(0, 32).forEach(i -> {
                String i_str = Integer.toString(i);

                System.out.println(" [x] Requesting fib(" + i_str + ")");

                String response = null;

                try {
                    response = fibonacciRPC.call(i_str);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(" [.] Got '" + response + "'");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String call(String msg) throws Exception {
        final String correlationId = UUID.randomUUID().toString();

        // unique Queue create
        String replyQueueName = channel.queueDeclare().getQueue();

        // properties setting
        AMQP.BasicProperties props = new AMQP.BasicProperties
            .Builder()
            .correlationId(correlationId)
            .replyTo(replyQueueName)
            .build();

        // Send Request
        channel.basicPublish("", RPC_QUEUE_NAME, props, msg.getBytes(StandardCharsets.UTF_8));

        //차단하는 Queue...? 추측: correlationId 가 다르면 차단
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        DeliverCallback callback = (consumerTag, delivery) -> {
            //되받은 메세지에 담긴 correlationId를 요청보낸 correlationId와 대조
            if (delivery.getProperties().getCorrelationId().equals(correlationId)) {
                response.offer(new String(delivery.getBody(), StandardCharsets.UTF_8));
            }
        };

        String ctag = channel.basicConsume(replyQueueName, true, callback, consumerTag -> { });
        String result = response.take();
        channel.basicCancel(ctag);

        return result;
    }

    @Override
    public void close() throws Exception {
        this.conn.close();
    }
}
