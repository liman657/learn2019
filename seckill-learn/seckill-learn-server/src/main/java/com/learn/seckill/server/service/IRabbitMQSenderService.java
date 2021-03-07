package com.learn.seckill.server.service;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment: RabbitMQ消息发送服务
 */
public interface IRabbitMQSenderService {

    public void sendKillSuccessEmailMsg(String orderNo);

    public void sendOrderInfo2Deal(String orderNo);

}
