package com.learn.springboot.redis.service.scheduler;

import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.User;
import com.learn.springboot.redis.service.service.email.EmailService;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 发送通告到商户的thread
 * @Author:debug (SteadyJack)
 * @Link: weixin-> debug0868 qq-> 1948831260
 * @Date: 2019/10/30 15:24
 **/
public class NoticeThread implements Callable<Boolean>{

    private User user;

    private Notice notice;

    private EmailService emailService;

    public NoticeThread(User user, Notice notice, EmailService emailService) {
        this.user = user;
        this.notice = notice;
        this.emailService = emailService;
    }

    @Override
    public Boolean call() throws Exception {
        emailService.emailUserNotice(notice,user);
        return true;
    }
}

































