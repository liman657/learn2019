package com.learn.starter.format.autoconfiguration;

import com.learn.starter.format.FormatProcessor;
import com.learn.starter.format.impl.JsonFormatProcessor;
import com.learn.starter.format.impl.StringFormatProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 * 通常情况下，formatAutoConfiguration在一般的项目中是单独的模块存在。
 * 这里为了简单，就使用一个文件夹
 */
@Configuration
public class FormatAutoConfiguration {

    @ConditionalOnMissingClass("com.alibaba.fastjson.JSON")
    @Bean
    @Primary
    public FormatProcessor stringFormat(){
        return new StringFormatProcessor();
    }

    @ConditionalOnClass(name="com.alibaba.fastjson.JSON")
    @Bean
    public FormatProcessor jsonFormat(){
        return new JsonFormatProcessor();
    }
}
