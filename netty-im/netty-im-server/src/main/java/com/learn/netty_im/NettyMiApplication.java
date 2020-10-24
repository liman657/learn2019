package com.learn.netty_im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * autor:liman
 * createtime:2019/11/20
 * comment:Server端的启动类
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-jdbc.xml"})
@MapperScan(basePackages = "com.learn.netty_im.mapper")
//@EnableAsync
//@EnableScheduling
@ComponentScan("com.learn.netty_im")
public class NettyMiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyMiApplication.class, args);
    }

}