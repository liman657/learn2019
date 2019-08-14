package com.learn.lambda.chapter02.stragegy;

import com.learn.lambda.Apple;

/**
 * autor:liman
 * createtime:2019/8/3
 * comment: 各种过滤器的接口
 */
@FunctionalInterface
public interface AppleFilter {

    public boolean filter(Apple apple);

}
