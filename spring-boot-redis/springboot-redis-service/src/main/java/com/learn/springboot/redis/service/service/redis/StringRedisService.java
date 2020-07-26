package com.learn.springboot.redis.service.service.redis;

import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:操作string的Redisservice
 */
@Service
@Slf4j
public class StringRedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ValueOperations getOperation(){
        return stringRedisTemplate.opsForValue();
    }

    /**
     * 设置key-value
     * @param key
     * @param value
     * @throws Exception
     */
    public void put(final String key,final String value) throws Exception{
        getOperation().set(RedisKeyConstants.RedisStringPrefix+key,value);
    }

    /**
     * 获取key-value
     * @param key
     * @return
     * @throws Exception
     */
    public Object get(final String key) throws Exception{
        return getOperation().get(RedisKeyConstants.RedisStringPrefix+key);
    }

    /**
     * 判断key-value是否存在
     * @param key
     * @return
     * @throws Exception
     */
    public Boolean exist(final String key) throws Exception{
        return stringRedisTemplate.hasKey(RedisKeyConstants.RedisStringPrefix+key);
    }

    /**
     * 设置指定的key-value过期时间
     * @param key
     * @param expireSeconds
     * @throws Exception
     */
    public void expire(final String key,final Long expireSeconds) throws Exception{
        stringRedisTemplate.expire(key,expireSeconds, TimeUnit.SECONDS);
    }

}
