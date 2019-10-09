package com.learn.springboot_mq.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/30.
 */
public class UserOrderDto implements Serializable{

    private String orderNo;

    private Integer userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserOrderDto{" +
                "orderNo='" + orderNo + '\'' +
                ", userId=" + userId +
                '}';
    }
}
