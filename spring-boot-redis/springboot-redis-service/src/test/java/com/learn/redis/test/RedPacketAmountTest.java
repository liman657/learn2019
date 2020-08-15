package com.learn.redis.test;

import com.learn.springboot.redis.service.SpringBootRedisApplication;
import com.learn.springboot.redis.service.util.RedPacketUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2020/7/31
 * comment:ZSet的测试类
 */
@Slf4j
public class RedPacketAmountTest {

    @Test
    public void testAmountMoney(){
        int totalMoney = 100;
        int count=10;
        List<Integer> moneyResult = RedPacketUtil.divideRedPackage(count, totalMoney);
        moneyResult.stream().reduce(Integer::sum).ifPresent(System.out::println);
    }

}
