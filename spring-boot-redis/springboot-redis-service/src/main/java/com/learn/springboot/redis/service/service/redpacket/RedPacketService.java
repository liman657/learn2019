package com.learn.springboot.redis.service.service.redpacket;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import com.learn.springboot.redis.model.dto.RedPacketDto;
import com.learn.springboot.redis.model.entity.RedDetail;
import com.learn.springboot.redis.model.entity.RedRecord;
import com.learn.springboot.redis.model.entity.RedRobRecord;
import com.learn.springboot.redis.model.mapper.RedDetailMapper;
import com.learn.springboot.redis.model.mapper.RedRecordMapper;
import com.learn.springboot.redis.model.mapper.RedRobRecordMapper;
import com.learn.springboot.redis.service.util.RedPacketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:抢红包的业务类
 */
@Service
@Slf4j
public class RedPacketService {

    private static final String RedPacketKey = "SpringBootRedis:RedPacket:%s:%s";
    //雪花算法，用于生成位于的全局id
    private static final Snowflake SNOWFLAKE = new Snowflake(3, 2);

    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public String handOut(RedPacketDto redPacketDto) {
        if (redPacketDto.getCount() > 0 && redPacketDto.getAmount() > 0) {
            //生成全局的红包id
            String redKey = String.format(RedPacketKey, redPacketDto.getUserId(), SNOWFLAKE.nextIdStr());
            //生成红包的随机金额
            List<Integer> redAmountList = RedPacketUtil.divideRedPackage(redPacketDto.getCount(), redPacketDto.getAmount());
            recordRedPacket(redPacketDto, redAmountList, redKey);
            //红包金额存入缓存
            saveRedAmount2Cache(redPacketDto, redAmountList, redKey);
            return redKey;
        }
        return null;
    }

    /**
     * 抢红包第一版
     * @param userId
     * @param redPacketKey
     * @return
     */
    public Integer robRedPacket(final Integer userId,final String redPacketKey){
        boolean isExistRedPacket = isExistRedPacket(redPacketKey);
        if(isExistRedPacket){
            Object value = redisTemplate.opsForList().rightPop(redPacketKey); //弹出一个金额
            if(value!=null){//抢到红包了
                //红包个数减少一
                final String redTotalKey = redPacketKey+":total";
//                Object currentTotalObject = redisTemplate.opsForValue().get(redPacketKey);
                ValueOperations valueOperations=redisTemplate.opsForValue();
                Object currentTotalObject = valueOperations.get(redTotalKey);
                Integer currentTotal = 0;
                if(currentTotalObject!=null){
                    currentTotal = Integer.valueOf(String.valueOf(currentTotalObject));
                    valueOperations.set(redTotalKey,currentTotal-1);
                }
                //将抢到的红包，存入数据库
                final Integer realRedPacketValue = Integer.valueOf(String.valueOf(value));
                recordRobRedPacket(userId,redPacketKey,realRedPacketValue);
                log.info("用户:{},抢到了红包，红包id为:{},抢到的金额为:{}",userId,redPacketKey,realRedPacketValue);
                return realRedPacketValue;
            }
        }else{
            log.info("用户:{}，来晚一步，红包被抢光了",userId);
        }
        return 0;
    }

    /**
     * 抢红包升级版，版本2
     * @param userId
     * @param redPacketKey
     * @return
     */
    public Integer robRedPacketVersion02(final Integer userId,final String redPacketKey){
        ValueOperations valueOperation = redisTemplate.opsForValue();

        //判断当前用户是否抢过红包
        final String redUserRobbedKey = redPacketKey+userId+":rob";
        Object isRobbedAmount = valueOperation.get(redUserRobbedKey);
        if(isRobbedAmount!=null){
            log.info("用户:{},已经抢过该红包了",userId);
            return 0;
        }

        boolean existRedPacket = isExistRedPacket(redPacketKey);
        if(existRedPacket){
            Object getRedValue = redisTemplate.opsForList().rightPop(redPacketKey);
            if(getRedValue!=null){
                //红包个数减一
                final String redCountKey = redPacketKey+":total";
                valueOperation.increment(redCountKey,-1L);

                //抢红包记录入库
                final Integer getRedAmount = Integer.valueOf(String.valueOf(getRedValue));
                recordRobRedPacket(userId,redPacketKey,getRedAmount);

                //记录当前用户获取的红包记录，避免同一个用户重复获取红包
                valueOperation.set(redUserRobbedKey,getRedValue,24L,TimeUnit.HOURS);
                log.info("用户:{},抢到了红包，红包id为:{},抢到的金额为:{}",userId,redPacketKey,getRedAmount);
                return getRedAmount;
            }
        }else{
            log.info("用户:{}，来晚一步，红包被抢光了",userId);
        }
        return 0;
    }

    /**
     * 一个小的随机红包 每个人只能抢一次；一个人每次只能抢到一个小的随机红包金额 - 永远保证1:1的关系
     * @param userId
     * @param redPacketKey
     * @return
     */
    public Integer robRedPackageVersion03(final Integer userId,final String redPacketKey){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        final String redUserRobbedKey = redPacketKey+userId+":rob";
        Object isRobbedAmount = valueOperations.get(redUserRobbedKey);
        if(isRobbedAmount!=null){
            log.info("用户:{},已经抢过该红包了",userId);
            return 0;
        }

        boolean existRed = isExistRedPacket(redPacketKey);
        if(existRed){
            final String lockKey = redPacketKey+userId+"-lock";
            boolean lock =valueOperations.setIfAbsent(lockKey,redPacketKey);
            try{
                if(lock){
                    redisTemplate.expire(lockKey,48L,TimeUnit.HOURS);
                    Object getRedValue = redisTemplate.opsForList().rightPop(redPacketKey);
                    if(getRedValue!=null){
                        //红包个数减一
                        final String redCountKey = redPacketKey+":total";
                        valueOperations.increment(redCountKey,-1L);

                        //抢红包记录入库
                        final Integer getRedAmount = Integer.valueOf(String.valueOf(getRedValue));
                        recordRobRedPacket(userId,redPacketKey,getRedAmount);

                        //记录当前用户获取的红包记录，避免同一个用户重复获取红包
                        valueOperations.set(redUserRobbedKey,getRedValue,24L,TimeUnit.HOURS);
                        log.info("用户:{},抢到了红包，红包id为:{},抢到的金额为:{}",userId,redPacketKey,getRedAmount);
                        return getRedAmount;
                    }
                }
            }catch (Exception e){

            }finally{
                //TODO:这里不需要释放锁，因为红包一发出去，就是一个新的key;一旦被抢完，生命周期永远终止
            }
        }
        return 0;
    }


    @Async
    public void recordRedPacket(RedPacketDto redPacketDto, List<Integer> redAmountList, String redKey) {
        RedRecord entity = new RedRecord();
        entity.setCreateTime(DateTime.now().toSqlDate());
        entity.setUserId(redPacketDto.getUserId());
        entity.setRedPacket(redKey);
        entity.setTotal(redPacketDto.getCount());
        entity.setAmount(BigDecimal.valueOf(redPacketDto.getAmount()));
        redRecordMapper.insertSelective(entity);//保存发红包人所发的金额记录.

        //保存每一个红包记录
        redAmountList.parallelStream().forEach(redAmount -> {
            RedDetail detail = new RedDetail();
            detail.setRecordId(entity.getId());
            detail.setAmount(BigDecimal.valueOf(redAmount));
            redDetailMapper.insertSelective(detail);
        });
    }

    @Async
    public void saveRedAmount2Cache(RedPacketDto redPacketDto, List<Integer> redAmountList, String redPacketKey) {
        //存入中的红包金额
        redisTemplate.opsForValue().set(redPacketKey + ":total", redPacketDto.getCount());
        //用一个list存放所有的小红包金额
        redisTemplate.opsForList().leftPushAll(redPacketKey, redAmountList);
    }

    /**
     * 判断是否存在红包个数
     * @param redPacketKey
     * @return
     */
    private boolean isExistRedPacket(final String redPacketKey) {
        Object total = redisTemplate.opsForValue().get(redPacketKey + ":total");
        if (total != null && Integer.valueOf(String.valueOf(total)) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 保存抢红包的金额记录
     * @param userId
     * @param redPacketKey
     * @param amount
     */
    @Async
    public void recordRobRedPacket(final Integer userId,final String redPacketKey,final Integer amount){
        RedRobRecord entity = new RedRobRecord();
        entity.setUserId(userId);
        entity.setRedPacket(redPacketKey);
        entity.setAmount(BigDecimal.valueOf(amount));
        entity.setRobTime(DateTime.now());
        redRobRecordMapper.insertSelective(entity);
    }


}
