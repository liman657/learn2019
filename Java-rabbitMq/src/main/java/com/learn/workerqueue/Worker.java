package com.learn.workerqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment:
 */
public class Worker {

    private static final String QUEUE_NAME = "hello_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //Note that we declare the queue here, as well. Because we might start the consumer before the publisher,
        // we want to make sure the queue exists before we try to consume messages from it.
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        //We're about to tell the server to deliver us the messages from the queue.
        // Since it will push us messages asynchronously,
        // we provide a callback in the form of an object that will buffer the messages
        // until we're ready to use them. That is what a DeliverCallback subclass does.
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try{
                doWork(message);
            }catch (Exception e){

            }finally {
                System.out.println("done");
            }
        };

        //手动确认消息机制是默认开启的，但是设置了自动确认之后，手动确认被关闭
        boolean autoAck = true;
        //正式消费消息，指定消费消息的回调内部类
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    private static void doWork(String message) throws InterruptedException {
//        for(char ch : task.toCharArray()){
//            if(ch=='.') Thread.sleep(5000);
//        }
        int times = Integer.valueOf(String.valueOf(message.charAt(message.length() - 1)));
        System.out.println("消费者处理"+times*2000+"毫秒");
        Thread.sleep(times*2000);
    }

}
