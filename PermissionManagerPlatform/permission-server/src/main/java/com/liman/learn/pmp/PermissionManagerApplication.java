package com.liman.learn.pmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * autor:liman
 * createtime:2020/12/30
 * comment:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.liman.learn.pmp.model.mapper")
public class PermissionManagerApplication extends SpringBootServletInitializer{

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PermissionManagerApplication.class);
    }

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(PermissionManagerApplication.class, args);
    }
}
