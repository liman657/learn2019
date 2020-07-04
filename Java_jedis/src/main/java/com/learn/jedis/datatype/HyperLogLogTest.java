package com.learn.jedis.datatype;

import redis.clients.jedis.Jedis;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:
 */
public class HyperLogLogTest {

    public static void main(String[] args) {
            Jedis jedis = new Jedis("192.168.72.128", 6379);

            float size = 10000;

            for (int i = 0; i < size; i++) {
                jedis.pfadd("hll", "hll-" + i);
            }
            long total = jedis.pfcount("hll");
            System.out.println(String.format("统计个数: %s", total));
            System.out.println(String.format("正确率: %s", (total / size)));
            System.out.println(String.format("误差率: %s", 1 - (total / size)));
            jedis.close();
    }
}
