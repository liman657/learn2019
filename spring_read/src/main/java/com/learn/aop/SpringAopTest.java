package com.learn.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * autor:liman
 * createtime:2019/6/9
 * comment:
 */
public class SpringAopTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-aop.xml");

        Arithmetic arithmetic = (Arithmetic) applicationContext.getBean("arithmetic");
        int result = arithmetic.add(1, 1);
        System.out.println(result);
    }
}