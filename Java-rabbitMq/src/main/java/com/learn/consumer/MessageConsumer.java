package com.learn.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/5
 * comment:
 */
public class MessageConsumer {
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;//rabbitmq 服务端默认端口号
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    public static void main(String[] args) throws IOException, TimeoutException {
        Address[] addresses = new Address[]{
                new Address(IP_ADDRESS, PORT)
        };
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);

        Connection connection = connectionFactory.newConnection(addresses);
        final Channel channel = connection.createChannel();
        channel.basicQos(64);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("开始消费消息：" + new String(body, "UTF-8"));
                System.out.println("获得的消息详情：consumerTag:" + consumerTag + "envelope:" + envelope.toString()
                        + "properties:" + properties.toString());
                channel.basicAck(envelope.getDeliveryTag(), false);//消息确认？
            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);//消费消息，第二个参数指定的是消费消息的回调函数
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.close();
        connection.close();
    }

}
