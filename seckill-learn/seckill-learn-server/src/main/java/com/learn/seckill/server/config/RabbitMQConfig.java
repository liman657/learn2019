package com.learn.seckill.server.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment:
 */
@Configuration
@Slf4j
public class RabbitMQConfig {

    @Autowired
    private Environment environment;
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);//设置异步的消费者个数
        factory.setMaxConcurrentConsumers(1);//设置最大的异步消费者个数
        factory.setPrefetchCount(1);//设置预拉取的消息个数
        factory.setTxSize(1);//设置交换机的个数
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //确认消费模式-NONE
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.simple.concurrency",int.class));
        factory.setMaxConcurrentConsumers(environment.getProperty("spring.rabbitmq.listener.simple.max-concurrency",int.class));
        factory.setPrefetchCount(environment.getProperty("spring.rabbitmq.listener.simple.prefetch",int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        connectionFactory.setPublisherConfirms(true);//投递成功是否需要确认
        connectionFactory.setPublisherReturns(true);//投递失败是否需要确认
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //设置消息成功投递的回调函数
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
            }
        });
        //设置消息投递失败的回调函数
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
            }
        });
        return rabbitTemplate;
    }

    //构建异步发送邮箱通知的消息模型
    @Bean
    public Queue successEmailQueue(){//消息队列的构建
        return new Queue(environment.getProperty("mq.kill.item.success.email.queue"),true);
    }
    @Bean
    public TopicExchange successEmailExchange(){//交换机的构建
        return new TopicExchange(environment.getProperty("mq.kill.item.success.email.exchange"),true,false);
    }
    @Bean
    public Binding successEmailBinding(){//二者的绑定
        return BindingBuilder.bind(successEmailQueue()).to(successEmailExchange()).with(environment.getProperty("mq.kill.item.success.email.routing.key"));
    }

    //构建订单超时未支付的队列模型
    @Bean
    public Queue successOrderDelayQueue(){
        Map<String,Object> argsMap = new HashMap<String,Object>();
        argsMap.put("x-dead-letter-exchange",environment.getProperty("mq.kill.item.success.kill.deal.exchange"));
        argsMap.put("x-dead-letter-routing-key",environment.getProperty("mq.kill.item.success.kill.deal.routing.key"));
        argsMap.put("x-message-ttl",10000);
        return new Queue(environment.getProperty("mq.kill.item.success.kill.firstDelay.queue"),true,false,false,argsMap);
    }

    /**
     * 第一次接受消息投递的交换机
     * @return
     */
    @Bean
    public TopicExchange successOrderDelayExchange(){
        return new TopicExchange(environment.getProperty("mq.kill.item.success.kill.delay.first.exchange"),true,false);
    }

    /**
     * （第一次接受消息）基本交换机+基本队列的绑定
     * @return
     */
    @Bean
    public Binding successOrderDelayBinding(){
        return BindingBuilder.bind(successOrderDelayQueue()).to(successOrderDelayExchange()).with(environment.getProperty("mq.kill.item.success.kill.delay.first.routing.key"));
    }

    @Bean
    public Queue realPayOrderQueue(){
        return new Queue(environment.getProperty("mq.kill.item.success.kill.order.deal.queue"),true);
    }

    @Bean
    public TopicExchange realPayOrderExchange(){
        return new TopicExchange(environment.getProperty("mq.kill.item.success.kill.deal.exchange"),true,false);
    }

    /**
     * 真正处理订单的交换机+队列的绑定
     * @return
     */
    @Bean
    public Binding successOrderDealBinding(){
        return BindingBuilder.bind(realPayOrderQueue()).to(realPayOrderExchange()).with(environment.getProperty("mq.kill.item.success.kill.deal.routing.key"));
    }

}