package com.learn.lambda.chapter02.stragegy;

import com.learn.lambda.Apple;

/**
 * autor:liman
 * createtime:2019/8/3
 * comment:
 */
public class WeightFilter implements AppleFilter {

    private int weight;
    private boolean biggerThan;//用于判断是大于还是小于

    public WeightFilter(int weight, boolean biggerThan) {
        this.weight = weight;
        this.biggerThan = biggerThan;
    }

    @Override
    public boolean filter(Apple apple) {
        if(biggerThan){
            return apple.getWeight()>weight;
        }else{
            return apple.getWeight()<weight;
        }
    }
}
