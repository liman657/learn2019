package com.learn.netty_im;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import javax.management.MXBean;

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

    /**
     * 手动将SpringUtil交给spring容器管理
     * @return
     */
    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(NettyMiApplication.class, args);
    }

}