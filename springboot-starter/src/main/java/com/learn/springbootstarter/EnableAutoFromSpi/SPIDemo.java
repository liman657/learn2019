package com.learn.springbootstarter.EnableAutoFromSpi;

import com.learn.service.SPIDemoService;
import com.learn.springbootstarter.configuration.ConfigurationDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/25
 * comment:
 */
@SpringBootApplication
public class SPIDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(SPIDemo.class);
        SPIDemoService sp = applicationContext.getBean(SPIDemoService.class);
//        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        Stream.of(beanDefinitionNames).forEach(System.out::println);
        String test = sp.SPISayHello("test");
        System.out.println(test);
    }

}
