package com.learn.seckill.server.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * autor:liman
 * createtime:2021/3/7
 * comment: redisson的配置类
 */
@Configuration
@Slf4j
public class RedissonConfig {

    @Autowired
    private Environment environment;

    @Bean
    public RedissonClient redissonClient(){
        Config redissonConfig = new Config();
        redissonConfig.useSingleServer()
                .setAddress(environment.getProperty("redis.config.host"))
                .setPassword(environment.getProperty("spring.redis.password"));//如果Redis没有密码，配置文件中可以不用配置spring.redis.password这个属性
        RedissonClient redissonClient = Redisson.create(redissonConfig);
        return redissonClient;
    }

}
