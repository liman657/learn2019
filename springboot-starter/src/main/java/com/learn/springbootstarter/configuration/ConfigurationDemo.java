package com.learn.springbootstarter.configuration;

import com.learn.springbootstarter.importDemo.OtherBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
public class ConfigurationDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationAnno.class);
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        Stream.of(beanDefinitionNames).forEach(System.out::println);
//        DemoClass bean = (DemoClass) applicationContext.getBean("getTestClass");
        DemoClass bean =  applicationContext.getBean(DemoClass.class);
        bean.sayDemoClass();

        OtherBean otherBean = applicationContext.getBean(OtherBean.class);
        System.out.println(otherBean.otherBean());
    }

}
