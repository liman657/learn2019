package com.learn.java8strategy.foundation.common;

import lombok.Data;

/**
 * autor:liman
 * createtime:2020/12/29
 * comment:
 */
@Data
public class Order {

    private int id;

    public Order(int id) {
        this.id = id;
    }
}
