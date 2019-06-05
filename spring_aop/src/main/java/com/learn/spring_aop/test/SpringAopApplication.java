package com.learn.spring_aop.test;

import com.learn.spring_aop.service.OrderService;
import com.learn.spring_aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:
 */
public class SpringAopApplication {
    public static void main(String[] args) {
        //启动IOC容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_aop_auto.xml");

        //取代理对象
        UserService userService = applicationContext.getBean(UserService.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);

        userService.createUser("liman","huifang",20);
        userService.queryUser();

        orderService.createOrder("liman","flower");
        orderService.queryOrder("liman");
    }
}
