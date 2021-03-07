package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.dto.KillSuccessUserInfo;
import com.learn.seckill.model.mapper.ItemKillSuccessMapper;
import com.learn.seckill.server.service.IRabbitMQSenderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment:
 */
@Service("rabbitMQSenderService")
@Slf4j
public class RabbitMQSenderServiceImpl implements IRabbitMQSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    /**
     * 发送秒杀成功的消息到消息中间件
     *
     * @param orderNo 订单编号
     */
    public void sendKillSuccessEmailMsg(String orderNo) {
        log.info("秒杀成功，订单:{}准备发送异步简单文本消息", orderNo);
        try {
            if (StringUtils.isNotBlank(orderNo)) {
                KillSuccessUserInfo userSuccesRobInfo = itemKillSuccessMapper.selectByCode(orderNo);
                if (null != userSuccesRobInfo) {
                    //TODO:rabbitmq发送消息的逻辑
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(environment.getProperty("mq.kill.item.success.email.exchange"));
                    rabbitTemplate.setRoutingKey(environment.getProperty("mq.kill.item.success.email.routing.key"));

                    //TODO：将info充当消息发送至队列
                    rabbitTemplate.convertAndSend(userSuccesRobInfo, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties messageProperties=message.getMessageProperties();
                            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,KillSuccessUserInfo.class);
                            return message;
                        }
                    });
                }
            }
        } catch (Exception e) {
            log.error("订单:{}秒杀成功之后发送通知邮件异常:{}", e);
        }
    }

    /**
     * 秒杀成功之后，将订单信息推送到MQ中，这里用到了延时队列，方便处理超时未支付的订单
     * @param orderNo
     */
    @Override
    public void sendOrderInfo2Deal(String orderNo) {
        log.info("开发发送用户订单到延时队列，订单号为:{}",orderNo);
        try{
            if(StringUtils.isNotBlank(orderNo)){
                KillSuccessUserInfo killSuccessUserInfo = itemKillSuccessMapper.selectByCode(orderNo);
                if(null!=killSuccessUserInfo){
                    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                    rabbitTemplate.setExchange(environment.getProperty("mq.kill.item.success.kill.delay.first.exchange"));
                    rabbitTemplate.setRoutingKey(environment.getProperty("mq.kill.item.success.kill.delay.first.routing.key"));
                    rabbitTemplate.convertAndSend(killSuccessUserInfo, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            MessageProperties messageProperties = message.getMessageProperties();
                            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                            messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,KillSuccessUserInfo.class);
                            //设置消息级别的动态ttl，这个设置会覆盖掉Queue中的ttl设置
                            final long ttlTime = 10000;//设置10秒的超时时间
//                            messageProperties.setExpiration(environment.getProperty("mq.kill.item.success.kill.expire"));
                            return message;
                        }
                    });
                }
            }
        }catch (Exception e){
            log.error("发送待处理订单到队列出现异常，异常信息为:{}",e);
        }
    }

}
