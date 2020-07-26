package com.learn.springboot.redis.service.service;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springboot.redis.model.entity.Item;
import com.learn.springboot.redis.model.mapper.ItemMapper;
import com.learn.springboot.redis.service.service.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:
 */
@Service
@Slf4j
public class StringService {

    private static final String KEY_PREFIX = "ITEM_";

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 添加商品
     * @param item
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addItem(Item item) throws Exception {
        //先保存的数据库再保存的redis
        item.setCreateTime(new Date());
        item.setId(null);
        itemMapper.insertSelective(item);
        Integer id = item.getId();

        if(id>0){
            stringRedisService.put(KEY_PREFIX+id.toString(),objectMapper.writeValueAsString(item));
        }
        return id;
    }

    /**
     * 获取商品
     * @param id
     * @return
     * @throws Exception
     */
    public Item getItem(Integer id) throws Exception {
        Item item = null;
        if(id!=null){
            //先取缓存中的数据
            if(stringRedisService.exist(KEY_PREFIX+id.toString())){
                String result=stringRedisService.get(id.toString()).toString();
                log.info("---string数据类型，从缓存中取出来的value：{}---",result);
                if(StrUtil.isNotBlank(result)){
                    item = objectMapper.readValue(result,Item.class);
                }
            }else{//如果缓存没有，则先从数据库区，取出之后并更新Redis
                log.info("---string数据类型，从数据库查询：id={}---",id);
                item=itemMapper.selectByPrimaryKey(id);
                if (item!=null){
                    stringRedisService.put(KEY_PREFIX+id.toString(),objectMapper.writeValueAsString(item));
                }
            }
        }
        return item;
    }
}