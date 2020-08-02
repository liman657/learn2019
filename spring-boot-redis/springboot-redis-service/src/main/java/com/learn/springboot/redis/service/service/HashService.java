package com.learn.springboot.redis.service.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.learn.springboot.redis.model.entity.SysConfig;
import com.learn.springboot.redis.model.mapper.SysConfigMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import com.learn.springboot.redis.service.service.redis.HashRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Service
@Slf4j
public class HashService {

    @Autowired
    private HashRedisService<SysConfig> hashRedisService;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    /**
     * 添加数据字典及其对应的选项(code-value)
     * @param config
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysConfigInfo(SysConfig config){
        try{
            int res = sysConfigMapper.insertSelective(config);
            if(res>0){
                List<SysConfig> configList = sysConfigMapper.selectActiveConfigs();
                if (configList!=null && !configList.isEmpty()) {
                    Map<String, List<SysConfig>> dataMap = Maps.newHashMap();

                    //TODO:所有的数据字典列表遍历 -> 转化为 hash存储的map
                    configList.forEach(t -> {
                        List<SysConfig> list = dataMap.get(t.getType());
                        if (list == null || list.isEmpty()) {
                            list = Lists.newLinkedList();
                        }
                        list.add(t);

                        dataMap.put(t.getType(), list);
                    });

                    hashRedisService.cacheConfigMap(RedisKeyConstants.RedisHashKeyConfig,dataMap);
                }
            }
        }catch (Exception e){
            log.error("实时获取所有有效的数据字典列表-转化为map-存入hash缓存中-发生异常：",e.fillInStackTrace());
        }
        return config.getId();
    }

    /**
     * 取出缓存中所有的数据字典列表
     * @return
     */
    public Map<String,List<SysConfig>> getAll(){
        return hashRedisService.getAllCacheConfigInfo(RedisKeyConstants.RedisHashKeyConfig);
    }

    /**
     * 取出缓存中特定的数据字典列表
     * @param type
     * @return
     */
    public List<SysConfig> getByType(final String type){
        return hashRedisService.getAllConfigInfoByType(RedisKeyConstants.RedisHashKeyConfig,type);
    }
}
