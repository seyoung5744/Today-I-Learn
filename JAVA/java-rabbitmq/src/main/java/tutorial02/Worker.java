package tutorial02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Worker {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("[*] Waiting for msgs. (Press CTRL + C to exit)");

        DeliverCallback callback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
            System.out.println("[x] Received : " + msg);

            try{
                doWork(msg);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, callback, consumerTag -> {});
    }

    private static void doWork(String task) throws InterruptedException{

        //점 1개당 1초 지연
        for(char ch: task.toCharArray())
            if(ch == '.') Thread.sleep(1000);
    }

}
