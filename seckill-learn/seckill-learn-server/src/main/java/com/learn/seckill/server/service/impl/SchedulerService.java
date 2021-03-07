package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.entity.ItemKillSuccess;
import com.learn.seckill.model.mapper.ItemKillSuccessMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/3/2
 * comment:批量任务处理类
 */
@Slf4j
@Component
public class SchedulerService {
    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    /**
     * 处理超时订单的批量任务
     */
    @Scheduled(cron="0/10 * * * * ?")
    public void dealDealyOrderInfoScheduler(){
        try{
            log.info("批量处理超时订单的任务开始");
            List<ItemKillSuccess> expireOrders = itemKillSuccessMapper.selectExpireOrders();
        }catch (Exception e){
            log.error("批量处理延时订单出现异常，异常信息为:{}",e);
        }
    }

}
