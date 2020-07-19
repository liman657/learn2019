package com.learn.jedis.sentinelPool;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/11
 * comment:
 */
@Slf4j
public class JedisSentinelTest {

    private static JedisSentinelPool pool;

    private static JedisSentinelPool createJedisPool() {
        // master的名字是sentinel.conf配置文件里面的名称
        String masterName = "redis-master";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("192.168.72.128:26379");
        sentinels.add("192.168.72.129:26379");
        sentinels.add("192.168.72.130:26379");
        pool = new JedisSentinelPool(masterName, sentinels);
        return pool;
    }

    public static void main(String[] args) {
        JedisSentinelPool pool = createJedisPool();
        pool.getResource().set("liman", "qq"+System.currentTimeMillis());
        System.out.println(pool.getResource().get("liman"));
    }
}
