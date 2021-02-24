package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.entity.ItemKill;
import com.learn.seckill.model.mapper.ItemKillMapper;
import com.learn.seckill.model.mapper.ItemKillSuccessMapper;
import com.learn.seckill.server.service.IRobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2021/2/24
 * comment:
 */
@Service("robService")
@Slf4j
public class RobServiceImpl implements IRobService {

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;
    @Autowired
    private ItemKillMapper itemKillMapper;

    @Override
    public Boolean killItem(Integer killId, Integer userId) throws Exception {

        boolean result = false;

        //1、判断当前用户是否已经抢购过该商品
        if(canRobProductItemByUserId(killId,userId)){
            //2、查询待秒杀商品详情
            ItemKill itemKill = itemKillMapper.selectById(killId);
            //3、判断该商品是否可以被秒杀
            if(canProductBeRobbed(itemKill)){
                //4、执行库存减一的操作（简单的库存扣减）
                int updateCount = itemKillMapper.updateKillItem(killId);
                if(updateCount > 0){//秒杀成功
                    //TODO:5、如果扣减成功，则生成秒杀订单，同时通知用户秒杀成功
                    result = true;
                }
            }

        }else{
            log.warn("用户:{},商品id:{}已抢购过该商品",userId,killId);
            throw new Exception("您已参与过该商品的抢购");
        }
        return result;
    }


    /**
     * 单纯的查询秒杀成功的数据表，如果有个数，则不能抢购，如果没有表示没有抢购过，可以进行抢购
     * SELECT
     *         COUNT(1) AS total
     *     FROM
     *         item_kill_success
     *     WHERE
     *         user_id = #{userId}
     *     AND kill_id = #{killId}
     *     AND `status` IN (0)
     * @param productId
     * @param userId
     *
     * @return
     */
    private boolean canRobProductItemByUserId(Integer productId,Integer userId){
        int canKillCount = itemKillSuccessMapper.countByKillUserId(productId, userId);
        if(canKillCount > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean canProductBeRobbed(ItemKill itemKill){
        if(null!=itemKill && 1 == itemKill.getCanKill()){
            return true;
        }else{
            return false;
        }
    }
}
