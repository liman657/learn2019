package com.learn.test;

import com.learn.service.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * autor:liman
 * createtime:2019/5/30
 * comment:
 */
public class MainTest {

    public static void main(String[] args) {
        /**
         * applicationContext 启动过程中，会负责完成对Bean的创建和注入，但是怎么注入？
         */
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        System.out.println("context,启动成功");
        MessageService messageService = applicationContext.getBean(MessageService.class);
        System.out.println(messageService.getMessage());
    }

}
