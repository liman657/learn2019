package com.learn.starter.format.autoconfiguration;

import com.learn.starter.format.FormatProcessor;
import com.learn.starter.format.HelloFormatTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
@Import(FormatAutoConfiguration.class)
@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {

    @Bean
    public HelloFormatTemplate helloFormatTemplate(FormatProcessor formatProcessor,HelloProperties helloProperties){
        return new HelloFormatTemplate(formatProcessor,helloProperties);
    }

}
