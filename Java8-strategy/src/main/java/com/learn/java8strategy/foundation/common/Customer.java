package com.learn.java8strategy.foundation.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/12/29
 * comment:
 */
@Data
public class Customer {

    private String name;
    private List<Order> orders = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public Customer addOrder(Order order){
        this.orders.add(order);
        return this;
    }
}
