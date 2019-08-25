package com.learn.springbootstarter.AutoImportSelector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
@SpringBootApplication
@EnableDefineService
public class EnableDemoMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext ca=SpringApplication.run(EnableDemoMain.class,args);

        String[] beanDefinitionNames = ca.getBeanDefinitionNames();

        Predicate<String> predicate = str->str.equals("com.learn.springbootstarter.AutoImportSelector.CacheService")
                || str.equals("com.learn.springbootstarter.AutoImportSelector.LoggerService");

        Arrays.asList(beanDefinitionNames).stream().filter(predicate).forEach(System.out::println);
    }
}
