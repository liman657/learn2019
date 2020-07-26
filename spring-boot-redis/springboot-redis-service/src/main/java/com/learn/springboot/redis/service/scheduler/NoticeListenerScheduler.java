package com.learn.springboot.redis.service.scheduler;

import com.google.common.collect.Lists;
import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.User;
import com.learn.springboot.redis.model.mapper.UserMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import com.learn.springboot.redis.service.service.email.EmailService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:
 */
@Slf4j
@Component
@EnableScheduling
public class NoticeListenerScheduler {

    private static final String noticeListenerKey = RedisKeyConstants.RedisListNoticeKey;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0/59 * * * * ?")
    public void noticeListenerScheduler(){
        log.info("=====定时任务开启，读取queue中的数据，并发送数据=====");
        ListOperations<String,Notice> listOperations = redisTemplate.opsForList();
        Notice notice = listOperations.rightPop(noticeListenerKey);
        while(null!=notice){
            log.info("======开始给指定商户：{}发送邮件=====");
            notice = listOperations.rightPop(noticeListenerKey);
        }
    }

    //TODO:发送通知给到不同的商户
    @Async("threadPoolTaskExecutor")
    private void noticeUser(Notice notice){
        if (notice!=null){
            List<User> list=userMapper.selectList();

            //TODO:写法一-java8 stream api触发
            /*if (list!=null && !list.isEmpty()){
                list.forEach(user -> emailService.emailUserNotice(notice,user));
            }*/


            //TODO:写法二-线程池/多线程触发
            try {
                if (list!=null && !list.isEmpty()){
                    ExecutorService executorService=Executors.newFixedThreadPool(4);
                    List<NoticeThread> threads= Lists.newLinkedList();

                    list.forEach(user -> {
                        threads.add(new NoticeThread(user,notice,emailService));
                    });

                    executorService.invokeAll(threads);
                }
            }catch (Exception e){
                log.error("近实时的定时任务检测-发送通知给到不同的商户-法二-线程池/多线程触发-发生异常：",e.fillInStackTrace());
            }
        }
    }

}
