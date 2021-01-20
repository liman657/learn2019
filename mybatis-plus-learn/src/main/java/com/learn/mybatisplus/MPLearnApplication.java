package com.learn.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment:mybatis 学习的启动类
 */
@SpringBootApplication
@MapperScan("com.learn.mybatisplus.dao")
public class MPLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(MPLearnApplication.class);
    }

}
