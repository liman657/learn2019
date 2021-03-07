package com.learn.seckill.server.service;

import com.learn.seckill.server.dto.MailDto;

/**
 * autor:liman
 * createtime:2021/2/27
 * comment: 异步发送邮件的service
 */
public interface IMailService {

    /**
     * 发送简单文本邮件
     * @param mailDto
     */
    public void sendSimpleTxtMaiil(final MailDto mailDto);

    /**
     * 发送富文本邮件
     * @param mailDto
     */
    public void sendRichTxtMail(final MailDto mailDto);

}
