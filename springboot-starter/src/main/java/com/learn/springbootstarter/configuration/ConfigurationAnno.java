package com.learn.springbootstarter.configuration;

import com.learn.springbootstarter.importDemo.OtherConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment:
 */
@Configuration
@Import(OtherConfiguration.class)
public class ConfigurationAnno {

    @Bean
    public DemoClass getTestClass(){
        return new DemoClass();
    }

}
