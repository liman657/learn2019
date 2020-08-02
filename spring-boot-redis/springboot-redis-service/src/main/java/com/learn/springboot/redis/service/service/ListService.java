package com.learn.springboot.redis.service.service;

import com.google.common.collect.Lists;
import com.learn.springboot.redis.model.entity.Notice;
import com.learn.springboot.redis.model.entity.Product;
import com.learn.springboot.redis.model.mapper.NoticeMapper;
import com.learn.springboot.redis.model.mapper.ProductMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import com.learn.springboot.redis.service.service.redis.ListRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:List的业务Service
 */
@Slf4j
@Service
public class ListService {

    @Autowired
    private ListRedisService listRedisService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 添加商品
     * @param product
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer addProduct(Product product){
        if(product!=null){
            product.setId(null);
            productMapper.insertSelective(product);
            Integer id = product.getId();
            if(id>0){
                listRedisService.pushRedisObject(product);
            }
            return id;
        }
        return -1;
    }

    public List<Product> getHistoryProducts(final Integer userId){
        log.info("=====开始查询productList:{}=====",userId);
        List<Product> productList = Lists.newLinkedList();
        final String productKey = RedisKeyConstants.RedisListPrefix+userId;
        productList = listRedisService.getRedisListObject(productKey, false);
        if(1>productList.size()){//如果缓存中没有则直接去数据库中取数据，并更新缓存
            productList=productMapper.listProductsByUId(userId);
//            productList.stream().forEach(p->listRedisService.pushListIntoRedis(p));
            listRedisService.pushListIntoRedis(productKey,productList);//这种是List中只有一个元素，这个元素是一个list
        }
        log.info("=====查询的productList结果为:{}=====",productList);
        return productList;
    }

    /**
     * 将需要发送的数据放入到redis队列缓存
     * @param notice
     */
    @Transactional(rollbackFor = Exception.class)
    public void pushNotice(Notice notice){
        if(notice!=null){
            notice.setId(null);
            noticeMapper.insertSelective(notice);
            final Integer noticeId = notice.getId();

            if(noticeId>0){
                listRedisService.pushNoticeInRedis(notice);
            }
        }
    }


}
