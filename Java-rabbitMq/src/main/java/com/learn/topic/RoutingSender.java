package com.learn.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment:用于说明routingkey的消息发送者
 */
public class RoutingSender {

    private static final String EXCHANGE_NAME="topic_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        //connection与MQ主机获得连接
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");//声明队列的时候指定type

        String queueName = "";
        String message = "";
        String routeKey = "";
        for(int i = 0;i<100;i++){
            if(i%3==0){
                queueName = "black.queue";
            }else{
                queueName = "orange.queue";
            }
            message = "this message color is ["+i+"]"+queueName;
            routeKey = queueName+".key";
            System.out.println("开始发送消息："+message);

            //发送消息的时候指定routeKey
            channel.basicPublish(EXCHANGE_NAME,routeKey,null,message.getBytes("UTF-8"));
        }

    }

}
