package com.learn.designModel.ProxyPattern.staticProxy.DBDynamic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * autor:liman
 * comment:
 */
public class OrderServiceStaticProxy implements IOrderService{

    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");


    private IOrderService orderService;

    public OrderServiceStaticProxy(IOrderService orderService){
        this.orderService = orderService;
    }

    @Override
    public int createOrder(Order order) {
        //在真正进行订单创建之前进行一些操作
        Long time = order.getCreateTime();
        Integer dbRoute = Integer.valueOf(yearFormat.format(new Date(time)));
        System.out.println("静态代理类自动分配到【DB_"+dbRoute+"】数据源处理数据");
        DynamicDataSource.set(dbRoute);

        int result=orderService.createOrder(order);
        DynamicDataSource.restore();
        return result;
    }
}
