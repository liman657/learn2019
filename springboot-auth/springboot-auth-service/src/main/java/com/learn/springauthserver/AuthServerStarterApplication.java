package com.learn.springauthserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * autor:liman
 * createtime:2019/11/20
 * comment:Server端的启动类
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-jdbc.xml"})
@MapperScan(basePackages = "com.learn.springauthmodel.mapper")
@EnableAsync
@EnableScheduling
public class AuthServerStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerStarterApplication.class, args);
    }

}