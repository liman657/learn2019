package com.learn.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment: 订阅Orange类消息的消费者
 */
public class RoutingWorkerAll {

//    private static final String QUEUE_NAME = "orange_queue";
    private static final String EXCHANGE_NAME = "topic_logs";

    private static int count = 0;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String queueName = channel.queueDeclare().getQueue();
        String routeKey = "*.queue.key";
        channel.queueBind(queueName,EXCHANGE_NAME,routeKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received ["+(++count)+"]'" + message + "'");
        };

        //手动确认消息机制是默认开启的，但是设置了自动确认之后，手动确认被关闭
        boolean autoAck = true;
        //正式消费消息，指定消费消息的回调内部类
        channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> { });
    }


}
