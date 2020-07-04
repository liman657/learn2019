package com.learn.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:
 */
@Slf4j
public class SimpleTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.72.128", 6379);
        jedis.set("liman", "2673");
        log.info("jedis simple get result:{}",jedis.get("liman"));
        jedis.close();
    }
}
