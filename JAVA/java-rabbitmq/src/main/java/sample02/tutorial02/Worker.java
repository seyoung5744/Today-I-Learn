package sample02.tutorial02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Worker {

    private final static String QUEUE_NAME = "TASK_QUEUE";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        //durable
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit)");

        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        
        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println("[x] Received : " + msg);

            try{
                doWork(msg);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
                long deliveryTag = delivery.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
            }
        };

//        boolean autoAck = true;
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, callback, consumerTag -> {});
    }

    private static void doWork(String task) throws InterruptedException{

        //점 1개당 1초 지연
        for(char ch: task.toCharArray())
            if(ch == '.') Thread.sleep(1000);
    }

}
