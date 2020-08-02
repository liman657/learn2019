package com.learn.springboot.redis.service.service;

import com.google.common.collect.Lists;
import com.learn.springboot.redis.model.dto.FareDto;
import com.learn.springboot.redis.model.entity.PhoneFare;
import com.learn.springboot.redis.model.mapper.PhoneFareMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import com.learn.springboot.redis.service.service.redis.ZSetRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:手机话费的充值的业务类
 */
@Slf4j
@Service
public class ZSetService {

    @Autowired
    private ZSetRedisService<PhoneFare> zSetRedisService;

    @Autowired
    private ZSetRedisService<String> zSetUpdateRedisService;

    @Autowired
    private PhoneFareMapper fareMapper;


    /**
     * 新增手机话费充值记录
     * @param fare
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addRecord(PhoneFare fare) throws Exception{
        log.info("-----sorted set话费充值记录新增：{}----- ",fare);

        int res=fareMapper.insertSelective(fare);
        if (res>0){
            zSetRedisService.addElement(RedisKeyConstants.RedisSortedSetKeyOne,fare,fare.getFare().doubleValue());
        }
        return fare.getId();
    }

    /**
     * 获取充值排行榜
     * @param isAsc
     * @return
     */
    public Set<PhoneFare> getSortFares(final Boolean isAsc){
        return zSetRedisService.getSortedList(RedisKeyConstants.RedisSortedSetKeyOne, Optional.ofNullable(isAsc).orElse(true));
    }

    /**
     * 排序记录
     * @param fare
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addRecordUpdate(PhoneFare fare) throws Exception{
        log.info("-----sorted set 话费充值新增记录[更新]:{}-----",fare);
        int res = fareMapper.insertSelective(fare);
        if(res>0){
            double score = zSetUpdateRedisService.getScore(RedisKeyConstants.RedisSortedSetKey2, fare.getPhone());
            log.info("===score:{}===",score);
            if(score>0){//如果手机号已经充值过了，则直接加金额即可
                zSetUpdateRedisService.incrementScore(RedisKeyConstants.RedisSortedSetKey2,fare.getPhone(),fare.getFare().doubleValue());
            }else{
                zSetUpdateRedisService.addElement(RedisKeyConstants.RedisSortedSetKey2,fare.getPhone(),fare.getFare().doubleValue());
            }
        }
        return fare.getId();
    }

    /**
     * 升级版的获取用户充值排序记录
     * @return
     */
    public List<PhoneFare> getSortFaresUpdate(){
        List<PhoneFare> result = Lists.newLinkedList();

        Set<ZSetOperations.TypedTuple<String>> scoreSet
                = zSetUpdateRedisService.getUpdateSortedList(RedisKeyConstants.RedisSortedSetKey2, true);

        if(null!=scoreSet && !scoreSet.isEmpty()){
            scoreSet.forEach(
                    t->{
                        PhoneFare fare = new PhoneFare();
                        fare.setFare(BigDecimal.valueOf(t.getScore()));
                        fare.setPhone(t.getValue());
                        result.add(fare);
                    }
            );
        }
        return result;
    }
}
