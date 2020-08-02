package com.learn.redis.test;

import com.google.common.collect.Lists;
import com.learn.springboot.redis.service.SpringBootRedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

/**
 * autor:liman
 * createtime:2020/7/27
 * comment:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootRedisApplication.class)
@Slf4j
public class ListRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testListRedis(){
        log.info("-------开始Redis列表数据结构的测试------");
        final String listTestKey = "SpringBootRedis:List:Test";
        redisTemplate.delete(listTestKey);

        ListOperations<String,String> listOperations = redisTemplate.opsForList();

        ArrayList<String> list = Lists.newArrayList("A", "B", "C");
        listOperations.leftPush(listTestKey,"a");
        listOperations.leftPush(listTestKey,"b");
        listOperations.leftPushAll(listTestKey,list);

//        log.info("--当前列表元素个数：{} ", listOperations.size(listTestKey));
//        log.info("--当前列表元素：{} ", listOperations.range(listTestKey, 0, 10));
//
//        log.info("--当前列表中下标为0的元素：{} ", listOperations.index(listTestKey, 0L));
//        log.info("--当前列表中下标为4的元素：{} ", listOperations.index(listTestKey, 4L));
//        log.info("--当前列表中下标为10的元素：{} ", listOperations.index(listTestKey, 10L));
//
//        log.info("--当前列表从右边弹出来：{} ", listOperations.rightPop(listTestKey));

//        listOperations.set(listTestKey, 0L, "100");
//        log.info("--当前列表中下标为0的元素：{} ", listOperations.index(listTestKey, 0L));
//        log.info("--当前列表元素：{} ", listOperations.range(listTestKey, 0, 10));
//
//        listOperations.remove(listTestKey, 0L, "100");
//        log.info("--当前列表元素：{} ", listOperations.range(listTestKey, 0, 10));
    }
}
