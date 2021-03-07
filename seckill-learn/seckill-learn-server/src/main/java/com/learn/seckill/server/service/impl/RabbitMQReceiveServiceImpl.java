package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.dto.KillSuccessUserInfo;
import com.learn.seckill.model.entity.ItemKillSuccess;
import com.learn.seckill.model.mapper.ItemKillSuccessMapper;
import com.learn.seckill.server.dto.MailDto;
import com.learn.seckill.server.service.IMailService;
import com.learn.seckill.server.service.IRabbitMQReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment: 接受消息的服务类
 */
@Service("rabbitMQReceiveService")
@Slf4j
public class RabbitMQReceiveServiceImpl implements IRabbitMQReceiveService {
    @Autowired
    private IMailService mailService;
    @Autowired
    private Environment environment;
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    /**
     * 消费需要发送邮件的消息
     * 由于这里的消息生产者和消费者都是在一个应用中，这里就是消费的操作，这里也是真正发送邮件的地方
     * 这里使用的是单实例消费者
     * @param killSuccessUserInfo
     */
    @RabbitListener(queues = {"${mq.kill.item.success.email.queue}"},containerFactory = "singleListenerContainer")
    public void consumeEmailMsg(KillSuccessUserInfo killSuccessUserInfo){
        try{
            log.info("接收到MQ中的消息准备发送邮件:{}",killSuccessUserInfo);

            //TODO:真正的发送邮件....
            //MailDto dto=new MailDto(env.getProperty("mail.kill.item.success.subject"),"这是测试内容",new String[]{info.getEmail()});
            //mailService.sendSimpleEmail(dto);

            final String content=String.format(environment.getProperty("mail.kill.item.success.content"),killSuccessUserInfo.getItemName(),killSuccessUserInfo.getCode());
            MailDto dto=new MailDto(environment.getProperty("mail.kill.item.success.subject"),content,new String[]{killSuccessUserInfo.getEmail()});
//            mailService.sendSimpleTxtMaiil(dto);
            mailService.sendRichTxtMail(dto);
        }catch (Exception e){
            log.error("秒杀异步邮件通知-接收消息-发生异常：",e.fillInStackTrace());
        }

    }

    /**
     * 处理用户秒杀成功之后的订单信息
     * 这里使用了多实例消费者
     * @param userOrderInfo
     */
    @RabbitListener(queues = {"${mq.kill.item.success.kill.order.deal.queue}"},containerFactory = "multiListenerContainer")
    public void consumerOrderMsg(KillSuccessUserInfo userOrderInfo){
        log.info("开始处理超时的订单信息为:{}",userOrderInfo);
        try{
            //超时了，直接将原有订单失效处理
            if(null!=userOrderInfo){
                ItemKillSuccess seckillOrderInfo = itemKillSuccessMapper.selectByPrimaryKey(userOrderInfo.getCode());
                if(null!=seckillOrderInfo && 0==seckillOrderInfo.getStatus()){//如果还是未支付的，则表示，用户就是超时未支付，则需要直接失效订单
                    itemKillSuccessMapper.expireOrder(userOrderInfo.getCode());
                }

            }
        }catch (Exception e){
            log.error("消费订单数据，出现异常，异常信息为:{}",e);
        }
    }


}
