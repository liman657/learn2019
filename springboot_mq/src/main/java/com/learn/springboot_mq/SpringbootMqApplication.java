package com.learn.springboot_mq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-jdbc.xml"})
@MapperScan(basePackages = "com.learn.springboot_mq.mapper")
public class SpringbootMqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMqApplication.class, args);
	}

}
