package com.learn.springboot_mq.producer;

import com.learn.springboot_mq.config.RabbitConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * author:liman
 * createtime:2019/6/6
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    //由于rabbitTemplate的scope设置为ConfiburableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入。
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }

    public void sendMsg(String content){
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A,RabbitConfig.ROUTINGKEY_A,content);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("回调Id:"+correlationData);
        if(ack){
            System.out.println("消息成功消费");
        }else{
            System.out.println("消息消费失败："+cause);
        }
    }
}
