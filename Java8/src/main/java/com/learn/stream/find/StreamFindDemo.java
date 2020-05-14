package com.learn.stream.find;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * autor:liman
 * createtime:2020/5/14
 * comment: stream find操作的实例
 */
@Slf4j
public class StreamFindDemo {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        List<Integer> numList = Arrays.asList(1,2,3,4,5,6,6,9,10,7,8);
        //返回第一个
        Optional<Integer> findFirstResult = numList.stream().filter(i -> i % 2 == 0).findFirst();
        log.info("find first result : {}",findFirstResult.get());

        //返回任意一个，通常也是第一个
        Optional<Integer> findAnyResult = numList.stream().filter(i -> i % 2 != 0).findAny();
        log.info("find any result : {}",findAnyResult.get());

        Optional<Integer> first = numList.stream().findFirst();
        log.info("original first result : {}",first.get());

        Optional<Integer> any = numList.stream().findAny();
        log.info("original any result : {}",any.get());
    }

}
