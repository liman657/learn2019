package com.learn.stream.filter;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2020/5/14
 * comment:
 */
@Slf4j
public class FilterDemo {

    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(1,2,3,4,5,6,6,9,10,7,8);

        List<Integer> result = numList.stream().filter(i->i%2==0).collect(Collectors.toList());
        log.info("filter result : {}",result);

        //去重
        List<Integer> distinctReuslt = numList.stream().distinct().collect(Collectors.toList());
        log.info("distince result : {}",distinctReuslt);

        //取出最后5个
        List<Integer> skipResult = numList.stream().skip(5).collect(Collectors.toList());
        log.info("skip result:{}",skipResult);

        //取出前面5个
        List<Integer> limitResult = numList.stream().limit(5).collect(Collectors.toList());
        log.info("limit result:{}",limitResult);
    }

}
