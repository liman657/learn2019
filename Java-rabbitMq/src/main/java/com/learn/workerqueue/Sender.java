package com.learn.workerqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment:
 */
public class Sender {

    private final static String QUEUE_NAME = "hello_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        //connection与MQ主机获得连接
        Connection connection = connectionFactory.newConnection();

        //通过channel操作api
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        StringBuffer message = new StringBuffer("this is message with deal time ");
        for(int i = 1;i<=6;i++){
            message.append(i);
            //这里没有指定交换机
            /**
             * queueDeclare中第二个参数设置为true表示队列持久化，这里的第三个参数指定TEXT_PLAIN表示消息持久化
             */
            channel.basicPublish("",QUEUE_NAME,MessageProperties.TEXT_PLAIN,message.toString().getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
//
//
//        //这里没有指定交换机
//        /**
//         * queueDeclare中第二个参数设置为true表示队列持久化，这里的第三个参数指定TEXT_PLAIN表示消息持久化
//         */
//        channel.basicPublish("",QUEUE_NAME,MessageProperties.TEXT_PLAIN,message.getBytes());

    }

}
