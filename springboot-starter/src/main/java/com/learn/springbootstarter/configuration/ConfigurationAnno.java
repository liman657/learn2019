package com.learn.springbootstarter.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
@Configuration
public class ConfigurationAnno {

    @Bean
    public DemoClass getDemoClass(){
        return new DemoClass();
    }

}
