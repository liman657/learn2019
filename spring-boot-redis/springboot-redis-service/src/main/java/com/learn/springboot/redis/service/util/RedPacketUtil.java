package com.learn.springboot.redis.service.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * autor:liman
 * createtime:2020/8/11
 * comment:红包金额的生成——二倍均值法
 */
@Slf4j
public class RedPacketUtil {

    /**
     * 拆分红包的方法
     *
     * @param totalCount 拆分成的红包个数
     * @param totalMoney 总的红包金额
     * @return
     */
    public static List<Integer> divideRedPackage(int totalCount, int totalMoney) {
        List<Integer> amountList = Lists.newLinkedList();
        if (totalCount > 0 && totalMoney > 0) {
            int restMoney = totalMoney;
            int restCount = totalCount;
            int redAmount = 0;
            Random random = new Random();
            while (restCount > 1) {//由于随机数本身的机制，无法在固定的循环次数之内分配完成所有金额，因此这里循环n-1次
                redAmount = random.nextInt(restMoney / restCount * 2 - 1) + 1;
                amountList.add(redAmount);
                restMoney -= redAmount;
                restCount--;
            }
            //这里将剩余金额作为最后一个红包。
            amountList.add(restMoney);

        }
        log.info("最终分配的结果为:{}",amountList);
        return amountList;
    }
}