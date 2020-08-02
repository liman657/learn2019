package com.learn.redis.test;

import com.learn.springboot.redis.service.SpringBootRedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/31
 * comment:ZSet的测试类
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootRedisApplication.class)
@Slf4j
public class ZSetRedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testZSetRedis(){
        log.info("------开始有序集合SortedSet的测试-----");
        final String key = "SpringBootRedis:SortedSet:10010";
        redisTemplate.delete(key);//清空

        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,"a",8.0);
        zSetOperations.add(key,"b",2.0);
        zSetOperations.add(key,"c",4.0);
        zSetOperations.add(key,"d",6.0);
        log.info("---有序集合SortedSet-成员数：{}",zSetOperations.size(key));
        log.info("---有序集合SortedSet-按照分数正序：{}",zSetOperations.range(key,0L,zSetOperations.size(key)));
        log.info("---有序集合SortedSet-按照分数倒序：{}",zSetOperations.reverseRange(key,0L,zSetOperations.size(key)));
        log.info("---有序集合SortedSet-获取成员a的得分：{}",zSetOperations.score(key,"a"));
        log.info("---有序集合SortedSet-获取成员c的得分：{}",zSetOperations.score(key,"c"));

        log.info("---有序集合SortedSet-正序中c的排名：{} 名",zSetOperations.rank(key,"c")+1);
        log.info("---有序集合SortedSet-倒序中c的排名：{} 名",zSetOperations.reverseRank(key,"c"));

        zSetOperations.incrementScore(key,"b",10.0);
        log.info("---有序集合SortedSet-按照分数倒序：{}",zSetOperations.reverseRange(key,0L,zSetOperations.size(key)));

        zSetOperations.remove(key,"b");
        log.info("---有序集合SortedSet-按照分数倒序：{}",zSetOperations.reverseRange(key,0L,zSetOperations.size(key)));

        log.info("---有序集合SortedSet-取出分数区间的成员：{}",zSetOperations.rangeByScore(key,0,7));

        log.info("---有序集合SortedSet-取出带分数的排好序的成员：");
        Set<ZSetOperations.TypedTuple<String>> set=zSetOperations.rangeWithScores(key,0L,zSetOperations.size(key));
        set.forEach(tuple -> log.info("--当前成员：{} 对应的分数：{}",tuple.getValue(),tuple.getScore()));

    }

}
