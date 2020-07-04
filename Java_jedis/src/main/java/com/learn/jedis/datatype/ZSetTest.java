package com.learn.jedis.datatype;

import com.learn.jedis.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:ZSet的实例
 */
@Slf4j
public class ZSetTest {

    public static void main(String[] args) {
//        JedisUtil.getJedisUtil().zadd("myzset", 20, "java");
//        JedisUtil.getJedisUtil().zadd("myzset", 30, "python");
//        JedisUtil.getJedisUtil().zadd("myzset", 90, "ruby");
//        JedisUtil.getJedisUtil().zadd("myzset", 40, "erlang");
//        JedisUtil.getJedisUtil().zadd("myzset", 70, "cpp");
//        JedisUtil.getJedisUtil().zadd("myzset", 50, "android");
//        Set<String> set = JedisUtil.getJedisUtil().zrangeByScore("myzset", 100, 10);
//        log.info("set:{}",set);
        Jedis jedis = new Jedis("192.168.72.128", 6379);
        //zadd 往zset中增加元素
        jedis.zadd("myzset", 20, "java");
        jedis.zadd("myzset", 30, "python");
        jedis.zadd("myzset", 90, "ruby");
        jedis.zadd("myzset", 40, "erlang");
        jedis.zadd("myzset", 70, "cpp");
        jedis.zadd("myzset", 50, "android");

        //按照分数倒序排列
        Set<String> set = jedis.zrevrangeByScore("myzset", 100, 10);
        log.info("redis zset content:{}",set);
    }

}
