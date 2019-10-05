package com.learn.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/5
 * comment:
 */
public class MessageProducer {

    private static final String EXCHANGE_NAME = "exchange_name";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "queue_demo";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;//RabbitMQ 服务端默认端口号
    private static final String USERNAME = "guest";
    private static final String PASSWORD = "guest";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.生产者获得与MQ的连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername(USERNAME);
        connectionFactory.setPassword(PASSWORD);

        //2.获得连接
        Connection connection = connectionFactory.newConnection();
        
        //3.获取channel
        Channel channel = connection.createChannel();
        //4.创建一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        //5.创建一个队列。
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //6.绑定一个队列。
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "hello world this is rabbit MQ";

        //7.发送一个持久化的消息
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();

    }
}
