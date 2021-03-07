package com.learn.seckill.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * autor:liman
 * createtime:2021/3/7
 * comment: zookeeper的配置类
 */
@Configuration
@Slf4j
public class ZookeeperConfig {

    @Autowired
    private Environment environment;

    /**
     * 自定义注入zookeeper客户端操作实例
     * @return
     */
    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(environment.getProperty("zk.host"))
                .namespace(environment.getProperty("zk.namespace"))
                .retryPolicy(new RetryNTimes(3,10))//重试策略
                .build();
        curatorFramework.start();
        return curatorFramework;
    }

}
