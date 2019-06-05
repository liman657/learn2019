package com.learn.spring_aop.service.impl;

import com.learn.spring_aop.entity.Order;
import com.learn.spring_aop.service.OrderService;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:
 */
public class OrderServiceImpl implements OrderService {

    private static Order order = null;

    @Override
    public Order createOrder(String username, String product) {
        order = new Order();
        order.setUsername(username);
        order.setProduct(product);
        return order;
    }

    @Override
    public Order queryOrder(String username) {
        return null;
    }
}
