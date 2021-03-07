package com.learn.seckill.server.service.impl;

import com.learn.seckill.server.dto.MailDto;
import com.learn.seckill.server.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment: 发送邮件的服务
 */
@Service("mailService")
@Slf4j
@EnableAsync
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Environment environment;

    /**
     * 简单的文本邮件
     * @param mailDto
     */
    @Async
    public void sendSimpleTxtMaiil(final MailDto mailDto){
        try{
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(environment.getProperty("mail.send.from"));
            mailMessage.setTo(mailDto.getTos());
            mailMessage.setSubject(mailDto.getSubject());
            mailMessage.setText(mailDto.getContent());
            mailSender.send(mailMessage);
            log.info("文本邮件：{}，发送成功",mailDto);
        }catch (Exception e){
            log.error("发送文本邮件出现异常，异常信息为:{}",e);
        }
    }

    @Async
    public void sendRichTxtMail(final MailDto mailDto){
        try {
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper messageHelper=new MimeMessageHelper(message,true,"utf-8");
            messageHelper.setFrom(environment.getProperty("mail.send.from"));
            messageHelper.setTo(mailDto.getTos());
            messageHelper.setSubject(mailDto.getSubject());
            messageHelper.setText(mailDto.getContent(),true);

            mailSender.send(message);
            log.info("发送富文本邮件:{},发送成功!",mailDto);
        }catch (Exception e){
            log.error("发送花哨邮件-发生异常： ",e.fillInStackTrace());
        }
    }
}
