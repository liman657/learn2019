package com.learn.springbootstarter.importDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * autor:liman
 * createtime:2019/8/25
 * comment:
 */
@Configuration
public class OtherConfiguration {

    @Bean
    public OtherBean getOtherBean(){
        return new OtherBean();
    }

}
