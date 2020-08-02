package com.learn.springboot.redis.service.service.redis;

import com.google.common.collect.Lists;
import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.Product;
import com.learn.springboot.redis.model.mapper.ProductMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:Redis 关于list 的操作
 */
@Slf4j
@Service
public class ListRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将product 放入到Redis list中
     * @param product
     */
    public void pushRedisObject(final Product product){
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(RedisKeyConstants.RedisListPrefix+product.getUserId(),product);
    }

    /**
     * 将notice 放入到Redis list中
     * @param notice
     */
    public void pushNoticeInRedis(final Notice notice){
        ListOperations<String,Notice> listOperations = redisTemplate.opsForList();
        listOperations.leftPush(RedisKeyConstants.RedisListNoticeKey,notice);
    }

    /**
     * pop获取list中的object
     * @param productKey
     * @return
     */
    public Product popRedisObject(final String productKey){
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        Product product = listOperations.rightPop(productKey);
        return product;
    }

    /**
     * 获取Redis中存入的productList
     * @param productKey
     * @param reverse 是否倒序
     * @return
     */
    public List<Product> getRedisListObject(final String productKey,boolean reverse){
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        List<Product> productList =Lists.newLinkedList();
        productList = listOperations.range(productKey,0,listOperations.size(productKey));
        if(!reverse){
            return productList;
        }else{
            Collections.reverse(productList);
            return productList;
        }
    }

    /**
     * 将一个数组批量压入到Redis中的list中。
     * @param productKey
     * @param productList
     * @return
     */
    public Long pushListIntoRedis(final String productKey,final List<Product> productList){
        ListOperations<String,Product> listOperations = redisTemplate.opsForList();
        Long aLong = listOperations.leftPushAll(productKey, productList);
        return aLong;
    }
}
