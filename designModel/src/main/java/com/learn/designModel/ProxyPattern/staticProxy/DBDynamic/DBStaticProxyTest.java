package com.learn.designModel.ProxyPattern.staticProxy.DBDynamic;

import java.util.Date;

/**
 * autor:liman
 * comment:数据源静态代理测试
 */
public class DBStaticProxyTest {

    public static void main(String[] args) {
        Order order = new Order();
        order.setCreateTime(new Date().getTime());
        IOrderService orderService = new OrderServiceStaticProxy(new OrderService());
        orderService.createOrder(order);
    }

}