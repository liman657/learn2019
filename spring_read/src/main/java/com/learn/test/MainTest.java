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
         *
         */
        String[] applicationFiles = new String[2];
        applicationFiles[0] = "classpath:applicationContext.xml";
        applicationFiles[1] = "classpath:applicationContext-test.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(applicationFiles);
        System.out.println("context,启动成功");
        MessageService messageService = applicationContext.getBean(MessageService.class);

        MessageService messageService1 = (MessageService) applicationContext.getBean("message");
        System.out.println(messageService.getMessage());
        System.out.println(messageService == messageService1);
    }

}
