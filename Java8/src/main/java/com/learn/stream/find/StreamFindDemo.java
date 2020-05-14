package com.learn.stream.match;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/5/14
 * comment: stream find操作的实例
 */
@Slf4j
public class StreamMatchDemo {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        List<Integer> numList = Arrays.asList(1,2,3,4,5,6,6,9,10,7,8);

        //这一堆集合数据中是不是所有的数据都是偶数
        boolean allMatchResult = numList.stream().allMatch(i -> i % 2 == 0);
        log.info("all match result : {}",allMatchResult);
        boolean allBiggerThanZero = numList.stream().allMatch(i->i>0);
        log.info("all match bigger than 0 : {}",allBiggerThanZero);

        //只要有一个能被3整除
        boolean anyMatchResult = numList.stream().anyMatch(i -> i % 3 == 0);
        log.info("any match result : {}",anyMatchResult);

        boolean anyLessThanZero = numList.stream().anyMatch(i -> i < 0);
        log.info("any less than zero result : {}",anyLessThanZero);

        boolean noMatchResult = numList.stream().noneMatch(i -> i < 0);
        log.info("no less than zero result : {}",noMatchResult);
    }

}
