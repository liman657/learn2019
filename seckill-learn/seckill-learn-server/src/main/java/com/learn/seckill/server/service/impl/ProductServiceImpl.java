package com.learn.seckill.server.service.impl;

import com.learn.seckill.model.entity.ItemKill;
import com.learn.seckill.model.mapper.ItemKillMapper;
import com.learn.seckill.model.mapper.ItemMapper;
import com.learn.seckill.server.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/22
 * comment:
 */
@Service("itemService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ItemKillMapper itemKillMapper;


    @Override
    public List<ItemKill> getKillItems() throws Exception {
        return itemKillMapper.selectAll();
    }

    /**
     * 获取秒杀详情
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public ItemKill getKillDetail(Integer id) throws Exception {
        ItemKill entity=itemKillMapper.selectById(id);
        if (entity==null){
            throw new Exception("获取秒杀详情-待秒杀商品记录不存在");
        }
        return entity;
    }
}
