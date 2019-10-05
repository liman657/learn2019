package com.learn.rabbitmq.message;

import com.learn.rabbitmq.util.ResourceUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * autor:liman
 * createtime:2019/10/1
 * comment:
 */
public class MessageProducer {
    private final static String QUEUE_NAME = "ORIGIN_QUEUE";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri(ResourceUtil.getKey("rabbitmq.uri"));

        //建立连接
        Connection conn = connectionFactory.newConnection();
        //创建消息信道
        Channel channel = conn.createChannel();

        Map<String, Object> messageContent = new HashMap<String, Object>();
        messageContent.put("name", "liman");
        messageContent.put("age", "18");

        AMQP.BasicProperties properties =
                new AMQP.BasicProperties().builder()
                        .deliveryMode(2)//2代表持久化
                        .contentEncoding("UTF-8")//编码
                        .expiration("10000")  // TTL，过期时间
                        .headers(messageContent) // 自定义属性
                        .priority(5) // 优先级，默认为5，配合队列的 x-max-priority 属性使用
                        .messageId(String.valueOf(UUID.randomUUID()))
                        .build();

        String msg = "hello this messge send from MQ";
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicPublish("",QUEUE_NAME,properties,msg.getBytes());

        channel.close();
        conn.close();

    }
}
