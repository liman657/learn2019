package com.learn.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment:
 */
public class Recev {

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
        };
        //正式消费消息，指定消费消息的回调内部类
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }

}
