package com.learn.seckill.server.service;

import com.learn.seckill.model.entity.ItemKill;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/22
 * comment:
 */
public interface IProductService {

    List<ItemKill> getKillItems() throws Exception;

    ItemKill getKillDetail(Integer id) throws Exception;

}
