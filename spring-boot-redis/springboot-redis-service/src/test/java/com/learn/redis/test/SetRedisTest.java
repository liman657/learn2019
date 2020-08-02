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
import org.springframework.data.redis.core.SetOperations;
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
public class SetRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testSetRedis(){
        log.info("======开始测试set集合======");
        final String keyOne = "SpringBootRedis:Set:10010";
        final String keyTwo = "SpringBootRedis:Set:10011";

        redisTemplate.delete(keyOne);
        redisTemplate.delete(keyTwo);

        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add(keyOne,new String[]{"a","b","c"});
        setOperations.add(keyTwo,new String[]{"b","e","f"});

        log.info("---集合keyOne的元素：{}", setOperations.members(keyOne));
        log.info("---集合keyTwo的元素：{}", setOperations.members(keyTwo));

        log.info("---集合keyOne随机取1个元素：{}", setOperations.randomMember(keyOne));
        log.info("---集合keyOne随机取n个元素：{}", setOperations.randomMembers(keyOne, 2L));

        log.info("---集合keyOne元素个数：{}", setOperations.size(keyOne));
        log.info("---集合keyTwo元素个数：{}", setOperations.size(keyTwo));

        log.info("---元素a是否为集合keyOne的元素：{}", setOperations.isMember(keyOne, "a"));
        log.info("---元素f是否为集合keyOne的元素：{}", setOperations.isMember(keyOne, "f"));

        log.info("---集合keyOne和集合keyTwo的差集元素：{}", setOperations.difference(keyOne, keyTwo));
        log.info("---集合keyOne和集合keyTwo的交集元素：{}", setOperations.intersect(keyOne, keyTwo));
        log.info("---集合keyOne和集合keyTwo的并集元素：{}", setOperations.union(keyOne, keyTwo));

        log.info("---从集合keyOne中弹出一个随机的元素：{}", setOperations.pop(keyOne));
        log.info("---集合keyOne的元素：{}", setOperations.members(keyOne));
        log.info("---将c从集合keyOne的元素列表中移除：{}", setOperations.remove(keyOne, "c"));
    }
}
