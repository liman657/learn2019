package com.liman.learn.pmp.config;

import com.liman.learn.pmp.shiro.ShiroVariable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * autor:liman
 * createtime:2021/2/6
 * comment: freemarker的配置类
 */
@Configuration
@Slf4j
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer(ShiroVariable shiroVariable){
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:/templates");

        //这里将shiroVariable这个对象放到了MVC中的view层,使得前端可以调用这个对象中的相关方法
        //Freemarker是一个比较轻量级的前端框架view渲染引擎。
        Map<String, Object> variables = new HashMap<>(1);
        variables.put("shiro", shiroVariable);
        configurer.setFreemarkerVariables(variables);

        Properties settings = new Properties();
        settings.setProperty("default_encoding", "utf-8");
        settings.setProperty("number_format", "0.##");
        configurer.setFreemarkerSettings(settings);
        return configurer;

    }

}
