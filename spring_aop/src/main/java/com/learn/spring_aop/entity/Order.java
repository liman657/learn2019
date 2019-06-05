package com.learn.spring_aop.entity;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:订单
 */
public class Order {

    private String username;
    private String product;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Order{" +
                "username='" + username + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
