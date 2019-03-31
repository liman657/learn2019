package com.learn.designModel.ProxyPattern.staticProxy.DBDynamic;

/**
 * autor:liman
 * comment:
 */
public class OrderDao {

    public int insert(Order order){
        System.out.println("OrderDao新增Order成功");
        return 1;
    }

}
