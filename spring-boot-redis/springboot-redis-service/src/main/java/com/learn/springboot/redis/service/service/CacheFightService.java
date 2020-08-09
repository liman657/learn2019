package com.learn.springboot.redis.service.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.learn.springboot.redis.model.entity.Item;
import com.learn.springboot.redis.model.mapper.ItemMapper;
import com.learn.springboot.redis.service.service.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2020/8/5
 * comment:缓存穿透模拟
 */
@Service
@Slf4j
public class CacheFightService {
    private static final Snowflake snowflake = new Snowflake(3, 2);

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private static final RateLimiter rateLimiter = RateLimiter.create(0.05);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ItemMapper itemMapper;

    private static int limitCount = 1;

    /**
     * 模拟缓存穿透的场景
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItem(Integer id) throws Exception {
        Item item = null;
        if (id != null) {
            if (stringRedisService.exist(id.toString())) {
                String result = stringRedisService.get(id.toString()).toString();
                if (StrUtil.isNotBlank(result)) {
                    item = objectMapper.readValue(result, Item.class);
                }
            } else {
                log.info("----缓存穿透，从数据库查询----");
                item = itemMapper.selectByPrimaryKey(id);
                if (item != null) {
                    stringRedisService.put(id.toString(), objectMapper.writeValueAsString(item));
                }
            }
        }
        return item;
    }

    /**
     * 模拟缓存穿透的场景 解决方案一
     * 该方案并不优，依旧会出现一堆线程进来同时查数据库的情况
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItemSolutionOne(Integer id) throws Exception {
        Item item = null;
        if (id != null) {
            if (stringRedisService.exist(id.toString())) {
                String result = stringRedisService.get(id.toString()).toString();
                if (StrUtil.isNotBlank(result)) {
                    //将查询出来的数据，json序列化之后存储
                    item = objectMapper.readValue(result, Item.class);
                }
            } else {
                log.info("----缓存穿透，从数据库查询解决方案一，解决方案一----");
                item = itemMapper.selectByPrimaryKey(id);
                if (item != null) {
                    stringRedisService.put(id.toString(), objectMapper.writeValueAsString(item));
                } else {
                    //如果数据库查不到，就直接将空字符串存入缓存，这里还不能存入null，因为存入null，序列化后会成为"null"的字符串
//                    stringRedisService.put(id.toString(),objectMapper.writeValueAsString(null));//空串序列化后会成为""""
                    stringRedisService.put(id.toString(), "");
                    stringRedisService.expire(id.toString(), 3600L);//同时设置一个过期时间
                }
            }
        }
        return item;
    }

    /**
     * 缓存穿透第二种解决方案
     * 加入限流
     *
     * @param id
     * @return
     */
    public Item getItemSolutioinTwo(Integer id) throws Exception {
        Item item = null;
        if (id != null) {
            if (stringRedisService.exist(id.toString())) {
                log.info("缓存命中，从缓存查询数据结果");
                String result = stringRedisService.get(id.toString()).toString();
                if (StrUtil.isNotBlank(result)) {
                    item = objectMapper.readValue(result, Item.class);
                }
            } else {
                if (rateLimiter.tryAcquire()) {
                    log.info("----缓存穿透次数:{}，从数据库查询[解决方案二]----",limitCount++);
                    item = itemMapper.selectByPrimaryKey(id);
                    if (item != null) {
                        stringRedisService.put(id.toString(), objectMapper.writeValueAsString(item));
                    } else {
                        //如果数据库查不到，就直接将空字符串存入缓存，这里还不能存入null，因为存入null，序列化后会成为"null"的字符串
//                        stringRedisService.put(id.toString(), objectMapper.writeValueAsString(""));//空串序列化后会成为""""
                        stringRedisService.put(id.toString(), "");
                        stringRedisService.expire(id.toString(), 3600L);//同时设置一个过期时间
                    }
                }
            }
        }
        return item;
    }
}