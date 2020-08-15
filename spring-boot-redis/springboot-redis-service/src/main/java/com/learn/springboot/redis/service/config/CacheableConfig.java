package com.learn.springboot.redis.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

/**
 * autor:liman
 * createtime:2020/8/15
 * comment:Cacheable的配置类
 */
@Configuration
@EnableCaching
public class CacheableConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration config=RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(120)).disableCachingNullValues();
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config)
                .transactionAware().build();
    }
}
