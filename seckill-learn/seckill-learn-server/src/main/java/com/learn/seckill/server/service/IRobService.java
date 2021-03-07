package com.learn.seckill.server.service;

import com.learn.seckill.model.dto.KillSuccessUserInfo;

/**
 * autor:liman
 * createtime:2021/2/24
 * comment:
 */
public interface IRobService {

    Boolean killItem(Integer killId,Integer userId) throws Exception;

    KillSuccessUserInfo getRobProductDetail(String orderNo);

    public Boolean killItemMySQLUpdate(Integer killId, Integer userId) throws Exception;

    public Boolean killItemRedisLockUpdate(Integer killId, Integer userId) throws Exception;

    public Boolean killItemRedissonLockUpdate(Integer killId, Integer userId) throws Exception;

    public Boolean killItemZookeeperLockUpdate(Integer killId, Integer userId) throws Exception;
}
