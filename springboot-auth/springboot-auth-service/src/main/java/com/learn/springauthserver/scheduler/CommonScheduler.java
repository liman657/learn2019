package com.learn.springauthserver.scheduler;

import com.learn.springauthmodel.mapper.AuthTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * autor:liman
 * createtime:2019/12/14
 * comment: 通用的定时任务
 */
@Component
//@EnableAsync
@Slf4j
public class CommonScheduler {

    @Autowired
    private AuthTokenMapper authTokenMapper;

    @Scheduled(cron = "0/60 * * * * ? ")//60S执行一次的表达式
    @Async("taskExecutor")
    public void deleteAnyInvalidateToken(){
        try{
            log.info("--删除无效token的批次开始执行--");
            authTokenMapper.deleteUnactiveToken();
        }catch (Exception e){
            log.error("--删除无效的token批次执行异常");
        }
        log.info("--删除无效token的批次执行结束--");
    }
}
