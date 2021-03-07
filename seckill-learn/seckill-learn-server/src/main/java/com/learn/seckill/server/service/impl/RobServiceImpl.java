package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.dto.KillSuccessUserInfo;
import com.learn.seckill.model.entity.ItemKill;
import com.learn.seckill.model.entity.ItemKillSuccess;
import com.learn.seckill.model.mapper.ItemKillMapper;
import com.learn.seckill.model.mapper.ItemKillSuccessMapper;
import com.learn.seckill.server.constant.SysConstant;
import com.learn.seckill.server.service.IRabbitMQSenderService;
import com.learn.seckill.server.service.IRobService;
import com.learn.seckill.server.util.RandomUtil;
import com.learn.seckill.server.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.joda.time.DateTime;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2021/2/24
 * comment:
 */
@Service("robService")
@Slf4j
public class RobServiceImpl implements IRobService {

    private static final String zookeeper_lock_path_prefix = "/rob/zklock";

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;
    @Autowired
    private ItemKillMapper itemKillMapper;
    @Autowired
    private IRabbitMQSenderService rabbitMQSenderService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private CuratorFramework curatorFramework;

    private SnowFlake snowFlake = new SnowFlake(2, 3);


    /**
     * 实际开发中，userId不用前端传递，而是我们后端去缓冲中获取
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItem(Integer killId, Integer userId) throws Exception {

        boolean result = false;

        //1、判断当前用户是否已经抢购过该商品
        if (canRobProductItemByUserId(killId, userId)) {
            //2、查询待秒杀商品详情
            ItemKill itemKill = itemKillMapper.selectById(killId);
            //3、判断该商品是否可以被秒杀
            if (canProductBeRobbed(itemKill)) {
                //4、执行库存减一的操作（简单的库存扣减）
                int updateCount = itemKillMapper.updateKillItem(killId);
                if (updateCount > 0) {//秒杀成功
                    //5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                    commonRecordKillSuccessInfo(itemKill, userId);
                    result = true;
                }
            }

        } else {
            log.warn("用户:{},商品id:{}已抢购过该商品", userId, killId);
            throw new Exception("您已参与过该商品的抢购");
        }
        return result;
    }

    /**
     * MySQL层面的一些优化控制
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItemMySQLUpdate(Integer killId, Integer userId) throws Exception {

        boolean result = false;

        //1、判断当前用户是否已经抢购过该商品
        if (canRobProductItemByUserId(killId, userId)) {
            //2、查询待秒杀商品详情
            ItemKill itemKill = itemKillMapper.selectById(killId);
            //3、判断该商品是否可以被秒杀
            if (canProductBeRobbed(itemKill)) {
                //4、执行库存减一的操作（简单的库存扣减）
                int updateCount = itemKillMapper.updateKillItem(killId);
                if (updateCount > 0) {//秒杀成功
                    //5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                    commonRecordKillSuccessInfo(itemKill, userId);
                    result = true;
                }
            }

        } else {
            log.warn("用户:{},商品id:{}已抢购过该商品", userId, killId);
            throw new Exception("您已参与过该商品的抢购");
        }
        return result;
    }

    /**
     * 基于Redis的分布式锁实现
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItemRedisLockUpdate(Integer killId, Integer userId) throws Exception {

        boolean result = false;
        //1、判断当前用户是否已经抢购过该商品
        if (canRobProductItemByUserId(killId, userId)) {
            ValueOperations valueOperations = stringRedisTemplate.opsForValue();
            final String key = new StringBuilder().append(killId).append(userId).append("_redisLockKey").toString();
            final String value = RandomUtil.generateOrderCode();//生成key，分布式锁中这个没啥实质性作用
            Boolean isRedisLock = valueOperations.setIfAbsent(key, value);
            if (isRedisLock) {//如果获取了分布式锁，则可以执行秒杀的相关操作了
                stringRedisTemplate.expire(key, 30, TimeUnit.SECONDS);//这里需要设置强锁的过期时间，避免死锁
                try {
                    //2、查询待秒杀商品详情
                    ItemKill itemKill = itemKillMapper.selectById(killId);
                    //3、判断该商品是否可以被秒杀
                    if (canProductBeRobbed(itemKill)) {
                        //4、执行库存减一的操作（简单的库存扣减）
                        int updateCount = itemKillMapper.updateKillItem(killId);
                        if (updateCount > 0) {//秒杀成功
                            //5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                            commonRecordKillSuccessInfo(itemKill, userId);
                            result = true;
                        }
                    }
                } catch (Exception e) {
                    log.warn("获取分布式锁失败（Redis）无法完成商品的抢购");
                    throw new Exception("抢购商品失败（Redis分布式锁）");
                } finally {
                    //释放Redis锁
                    if (value.equals(valueOperations.get(key).toString())) {
                        stringRedisTemplate.delete(key);
                    }
                }
            }

        } else {
            log.warn("用户:{},商品id:{}已抢购过该商品", userId, killId);
            throw new Exception("您已参与过该商品的抢购");
        }
        return result;
    }

    /**
     * 基于 redisson 的分布式锁实现
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItemRedissonLockUpdate(Integer killId, Integer userId) throws Exception {

        boolean result = false;
        final String redissonLockKey = new StringBuffer().append(killId).append(userId).append("redission_rob_lock_key").toString();
        RLock rLock = redissonClient.getLock(redissonLockKey);
        try {
            boolean redissonLock = rLock.tryLock(30, 10, TimeUnit.SECONDS);
            if (redissonLock) {
                //1、判断当前用户是否已经抢购过该商品
                if (canRobProductItemByUserId(killId, userId)) {
                    //2、查询待秒杀商品详情
                    ItemKill itemKill = itemKillMapper.selectById(killId);
                    //3、判断该商品是否可以被秒杀
                    if (canProductBeRobbed(itemKill)) {
                        //4、执行库存减一的操作（简单的库存扣减）
                        int updateCount = itemKillMapper.updateKillItem(killId);
                        if (updateCount > 0) {//秒杀成功
                            //5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                            commonRecordKillSuccessInfo(itemKill, userId);
                            result = true;
                        }
                    }
                } else {
                    log.warn("用户:{},商品id:{}已抢购过该商品", userId, killId);
                    throw new Exception("您已参与过该商品的抢购");
                }
            }
        } catch (Exception e) {
            log.warn("获取分布式锁失败（redisson）无法完成商品的抢购");
            throw new Exception("抢购商品失败（redisson 分布式锁）");
        } finally {
            rLock.unlock();
//            rLock.forceUnlock();//这一个是强制释放锁
        }
        return result;
    }

    /**
     * 基于 zookeeper 的分布式锁实现
     *
     * @param killId
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Boolean killItemZookeeperLockUpdate(Integer killId, Integer userId) throws Exception {

        boolean result = false;
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework,zookeeper_lock_path_prefix+killId+userId+"_lock");
        try{
            if(interProcessMutex.acquire(20L,TimeUnit.SECONDS)){//如果获取到锁
                //1、判断当前用户是否已经抢购过该商品
                if (canRobProductItemByUserId(killId, userId)) {
                    //2、查询待秒杀商品详情
                    ItemKill itemKill = itemKillMapper.selectById(killId);
                    //3、判断该商品是否可以被秒杀
                    if (canProductBeRobbed(itemKill)) {
                        //4、执行库存减一的操作（简单的库存扣减）
                        int updateCount = itemKillMapper.updateKillItem(killId);
                        if (updateCount > 0) {//秒杀成功
                            //5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                            commonRecordKillSuccessInfo(itemKill, userId);
                            result = true;
                        }
                    }
                } else {
                    log.warn("用户:{},商品id:{}已抢购过该商品", userId, killId);
                    throw new Exception("您已参与过该商品的抢购");
                }
            }
        }catch (Exception e){
            log.warn("zookeeper 未获取到分布式锁,异常信息为:{}",e);
        }finally {
            if(interProcessMutex!=null){
                interProcessMutex.release();
            }
        }



        return result;
    }


    /**
     * 根据订单编号查询秒杀详情
     *
     * @param orderNo
     * @return
     */
    @Override
    public KillSuccessUserInfo getRobProductDetail(String orderNo) {
        KillSuccessUserInfo killSuccessUserInfo = itemKillSuccessMapper.selectByCode(orderNo);
        return killSuccessUserInfo;
    }

    /**
     * 抢单成功之后的操作
     *
     * @param itemKill
     * @param userId
     */
    private void commonRecordKillSuccessInfo(ItemKill itemKill, Integer userId) {
        ItemKillSuccess itemKillSuccessEntity = new ItemKillSuccess();
        String orderNo = String.valueOf(snowFlake.nextId());

        itemKillSuccessEntity.setCode(orderNo);//雪花算法生成订单号
        itemKillSuccessEntity.setItemId(itemKill.getItemId());
        itemKillSuccessEntity.setKillId(itemKill.getId());
        itemKillSuccessEntity.setUserId(userId.toString());
        itemKillSuccessEntity.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        itemKillSuccessEntity.setCreateTime(DateTime.now().toDate());
//        if(itemKillSuccessMapper.countByKillUserId(itemKill.getId(),userId) <=0 ) {//即使引入双重检测，并发量超过1秒1w的时候，也无法控制住一人消费多次的问题
        int successCount = itemKillSuccessMapper.insertSelective(itemKillSuccessEntity);
        if (successCount > 0) {
            //这里会处理异步消息的通知，利用rabbitMQ
            rabbitMQSenderService.sendKillSuccessEmailMsg(orderNo);

            //这里需要将用户订单信息投递得到延时队列中，等待处理
            rabbitMQSenderService.sendOrderInfo2Deal(orderNo);
        }
//        }

    }


    /**
     * 单纯的查询秒杀成功的数据表，如果有个数，则不能抢购，如果没有表示没有抢购过，可以进行抢购
     * SELECT
     * COUNT(1) AS total
     * FROM
     * item_kill_success
     * WHERE
     * user_id = #{userId}
     * AND kill_id = #{killId}
     * AND `status` IN (0)
     *
     * @param productId
     * @param userId
     * @return
     */
    private boolean canRobProductItemByUserId(Integer productId, Integer userId) {
        int canKillCount = itemKillSuccessMapper.countByKillUserId(productId, userId);
        if (canKillCount > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断商品是否可以被秒杀
     *
     * @param itemKill
     * @return
     */
    private boolean canProductBeRobbed(ItemKill itemKill) {
        if (null != itemKill && 1 == itemKill.getCanKill()) {
            return true;
        } else {
            return false;
        }
    }
}
