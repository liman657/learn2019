package com.learn.rabbitmq.message;

import com.learn.rabbitmq.util.ResourceUtil;
import com.rabbitmq.client.*;

import java.io.IOException;


/**
 * autor:liman
 * createtime:2019/10/1
 * comment:
 */
public class MessageConsumer {

    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        // 建立连接
        Connection conn = factory.newConnection();
        // 创建消息通道
        Channel channel = conn.createChannel();

        // 声明队列（默认交换机AMQP default，Direct）
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" Waiting for message....");

        Consumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received message : '" + msg + "' " );
                System.out.println("messageId : " + properties.getMessageId() );
                System.out.println(properties.getHeaders().get("name") + " -- " + properties.getHeaders().get("age"));
            }
        };

        //开始获取消息
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);
    }

}
