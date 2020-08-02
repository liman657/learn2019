package com.learn.springboot.redis.service.service.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Service
@Slf4j
public class ZSetRedisService<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 通用的，往zset中就增加元素
     * @param key
     * @param obj
     * @param score
     */
    public void addElement(String key,T obj,Double score){
        ZSetOperations<String,T> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add(key,obj,score);
    }


    /**
     * 将所有的列表排序，升序或者倒序
     * @param key
     * @param isAsc
     * @return
     */
    public Set<T> getSortedList(final String key,final boolean isAsc){
        ZSetOperations<String,T> zSetOperations = redisTemplate.opsForZSet();
        Long allSize = zSetOperations.size(key);
        return isAsc ? zSetOperations.range(key,0L,allSize) : zSetOperations.reverseRange(key,0L,allSize);
    }

    /**
     * 给指定的key增加分值
     * @param key
     * @param obj
     * @param score
     */
    public void incrementScore(final String key,T obj,Double score){
        ZSetOperations<String,T> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.incrementScore(key,obj,score);
    }

    /**
     * 获取对应对象的分数
     * @param key
     * @param obj
     * @return
     */
    public double getScore(final String key,T obj){
        try{
            ZSetOperations<String,T> zSetOperations = redisTemplate.opsForZSet();
            return Optional.ofNullable(zSetOperations.score(key,obj)).orElse(0.0D);
        }catch (Exception e){
            log.error("异常:{}",e);
            return 0.0D;
        }

    }

    /**
     * 获取更新后的排行榜
     * @param key
     * @param isAsc
     * @return
     */
    public Set<ZSetOperations.TypedTuple<T>> getUpdateSortedList(final String key,final boolean isAsc){
        ZSetOperations<String,T> zSetOperations=redisTemplate.opsForZSet();
        final Long size=zSetOperations.size(key);
        return zSetOperations.reverseRangeWithScores(key,0L,size);
    }
}