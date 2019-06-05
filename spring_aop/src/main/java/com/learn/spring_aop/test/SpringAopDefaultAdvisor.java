package com.learn.spring_aop.test;

import com.learn.spring_aop.service.OrderService;
import com.learn.spring_aop.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * author:liman
 * createtime:2019/6/4
 */
public class SpringAopDefaultAdvisor {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_aop_defaultAdvisor.xml");
        UserService userService = applicationContext.getBean(UserService.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);

        userService.createUser("liman","huifang",20);
        userService.queryUser();

        orderService.createOrder("Liman","慧芳");
        orderService.queryOrder("liman");
    }

}
