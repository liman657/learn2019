package com.learn.collector.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:
 */
@Data
@Slf4j
public class Trader {

    private final String name;
    private final String city;

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
