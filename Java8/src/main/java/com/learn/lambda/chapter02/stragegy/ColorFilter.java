package com.learn.lambda.chapter02.stragegy;

import com.learn.lambda.Apple;

/**
 * autor:liman
 * createtime:2019/8/3
 * comment:
 */
public class ColorFilter implements AppleFilter {

    private String color;

    public ColorFilter(String color) {
        this.color = color;
    }

    @Override
    public boolean filter(Apple apple) {
        return color.equals(apple.getColor());
    }
}
