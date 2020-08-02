package com.learn.springboot.redis.service.service.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.learn.springboot.redis.model.mapper.SysConfigMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Slf4j
@Service
public class HashRedisService<T> {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 实时获取所有有效的数据字典列表-转化为map-存入hash缓存中
     *
     * @param key
     * @param dataMap
     */
    @Async
    public void cacheConfigMap(final String key, Map<String, List<T>> dataMap) {
        try {
            HashOperations<String, String, List<T>> hashOperations = redisTemplate.opsForHash();
            hashOperations.putAll(RedisKeyConstants.RedisHashKeyConfig, dataMap);
        } catch (Exception e) {
            log.error("实时获取所有有效的数据字典列表-转化为map-存入hash缓存中-发生异常：", e.fillInStackTrace());
        }
    }

    /**
     * 从缓存hash中获取所有的数据字典配置map
     *
     * @return
     */
    public Map<String, List<T>> getAllCacheConfigInfo(final String hashKey) {
        Map<String, List<T>> result = Maps.newHashMap();
        try {
            HashOperations<String, String, List<T>> hashOperations = redisTemplate.opsForHash();
            result = hashOperations.entries(hashKey);
        } catch (Exception e) {
            log.error("从缓存hash中获取所有的数据字典配置map-发生异常：", e.fillInStackTrace());
        }
        return result;
    }

    /**
     * 从缓存hash中获取特定的数据字典列表
     *
     * @param hashEntityKey
     * @return
     */
    public List<T> getAllConfigInfoByType(final String hashKey,final String hashEntityKey) {
        List<T> result = Lists.newLinkedList();
        try {
            HashOperations<String, String, List<T>> hashOperations = redisTemplate.opsForHash();
            result = hashOperations.get(hashKey, hashEntityKey);
        } catch (Exception e) {
            log.error("从缓存hash中获取特定的数据字典列表-发生异常：", e.fillInStackTrace());
        }
        return result;
    }
}
