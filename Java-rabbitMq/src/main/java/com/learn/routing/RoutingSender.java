package com.learn.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * autor:liman
 * createtime:2019/10/10
 * comment:用于说明routingkey的消息发送者
 */
public class RoutingSender {

//    private static final String QUEUE_NAME = "exchange_queue";

    private static final String EXCHANGE_NAME="logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        //connection与MQ主机获得连接
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");

        String queueName = "";
        String message = "";
        String routeKey = "";
        for(int i = 0;i<100;i++){

            if(i%3==0){
                queueName = "black";
            }else{
                queueName = "orange";
            }
            message = "this message color is ["+i+"]"+queueName;
            routeKey = queueName+"Key";
            System.out.println("开始发送消息："+message);
            channel.basicPublish(EXCHANGE_NAME,routeKey,null,message.getBytes("UTF-8"));
        }

    }

}
