package com.learn.springbootstarter.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
public class ConfigurationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationAnno.class);
        DemoClass bean = applicationContext.getBean(DemoClass.class);
        bean.sayDemoClass();
    }

}
