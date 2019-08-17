package com.learn.lambda.comparableDemo;

import com.learn.lambda.common.Apple;
import com.learn.lambda.common.AppleContainer;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author: liman
 * createtime: 2019/8/12
 * comment:
 * Comparable包含一个函数compareTo，如果一个类实现了Comparable接口，就意味着这个类本身支持排序。
 * Comparator接口，Comparable接口是在目标类上实现的，如果这个时候需要更换比较策略，就需要修改源代码了，而且还原来的比较方法又不能存在
 *                  Comparator类似一个策略模式。
 */
public class ComparableDemo {

    public static void main(String[] args) {
        List<Apple> apples = AppleContainer.initAppleList();
        System.out.println(apples);
        Collections.sort(apples,new AppWeightComparator());
        System.out.println(apples);

//        Collections.sort(apples);
//        System.out.println(apples);
    }
}

class AppWeightComparator implements Comparator<Apple>{

    @Override
    public int compare(Apple o1, Apple o2) {
        return o1.getColor().compareTo(o2.getColor());
    }
}