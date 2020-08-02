package com.learn.redis.test;

import com.google.common.collect.Maps;
import com.learn.springboot.redis.service.SpringBootRedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootRedisApplication.class)
public class HashRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void method5() {
        log.info("----开始哈希Hash测试");

        final String key = "SpringBootRedis:Hash:Key:v1";
        redisTemplate.delete(key);

        HashOperations<String,String,String> hashOperations=redisTemplate.opsForHash();
        hashOperations.put(key,"10010","zhangsan");
        hashOperations.put(key,"10011","lisi");

        Map<String,String> dataMap= Maps.newHashMap();
        dataMap.put("10012","wangwu");
        dataMap.put("10013","zhaoliu");
        hashOperations.putAll(key,dataMap);

        log.info("---哈希hash-获取列表元素： {} ",hashOperations.entries(key));
        log.info("---哈希hash-获取10012的元素： {} ",hashOperations.get(key,"10012"));
        log.info("---哈希hash-获取所有元素的field列表： {} ",hashOperations.keys(key));

        log.info("---哈希hash-10013成员是否存在： {} ",hashOperations.hasKey(key,"10013"));
        log.info("---哈希hash-10014成员是否存在： {} ",hashOperations.hasKey(key,"10014"));

        hashOperations.putIfAbsent(key,"10020","sunwukong");
        log.info("---哈希hash-获取列表元素： {} ",hashOperations.entries(key));

        log.info("---哈希hash-删除元素10010 10011： {} ",hashOperations.delete(key,"10010","10011"));
        log.info("---哈希hash-获取列表元素： {} ",hashOperations.entries(key));

        log.info("---哈希hash-获取列表元素个数： {} ",hashOperations.size(key));
    }
}
