package com.learn.spring_aop.service;

import com.learn.spring_aop.entity.Order;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:
 */
public interface OrderService {

    Order createOrder(String username, String product);

    Order queryOrder(String username);

}
