package com.learn.designModel.ProxyPattern.staticProxy.DBDynamic;

/**
 * autor:liman
 * comment:订单实体
 */
public class Order {

    private Object orderInfo;
    //根据订单时间进行按年分库
    private Long createTime;
    private String id;

    public Object getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(Object orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
