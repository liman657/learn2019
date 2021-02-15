package com.learn.jedis.datatype;

import com.learn.jedis.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:
 */
@Slf4j
public class HashTest {
    public static void main(String[] args) {
//        String h1 = JedisUtil.getJedisUtil().hget("h1", "a");
//        System.out.println(h1);
//
//        List<String> subMenuList = new ArrayList<String>();
//        subMenuList = JedisUtil.getJedisUtil().hmget("h1","a","b","c","d","e");
//        System.out.println(subMenuList);
        Jedis jedis = new Jedis("192.168.72.128", 6379);
        Map<String,String> hmap = new HashMap<>();
        hmap.put("h1","a");
        hmap.put("h2","b");
        hmap.put("h3","c");
        hmap.put("h4","d");
        hmap.put("h5","e");
        jedis.hmset("hmap",hmap);
        List<String> mapValue = jedis.hmget("hmap", "h1", "h2", "h3", "h4", "h5");
        log.info("hmapValue:{}",mapValue);
    }
}
