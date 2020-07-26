package com.learn.springboot.redis.service.service.email;

import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:发送邮件
 */
@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    //TODO:给指定的用户发送通告
    public void emailUserNotice(Notice notice, User user){
        log.info("----给指定的用户：{} 发送通告：{}",user,notice);

        this.sendSimpleEmail(notice.getTitle(),notice.getContent(),user.getEmail());
    }

    /**
     * 发送简单邮件
     * @param subject
     * @param content
     * @param tos
     */
    public void sendSimpleEmail(final String subject,final String content,final String ... tos){
        try {
            SimpleMailMessage message=new SimpleMailMessage();
            message.setSubject(subject);
            message.setText(content);
            message.setTo(tos);
            message.setFrom(environment.getProperty("mail.send.from"));
            mailSender.send(message);

            log.info("----发送简单的邮箱完毕--->");
        }catch (Exception e){
            log.error("--发送简单的邮件消息,发生异常：",e.fillInStackTrace());
        }
    }

}
