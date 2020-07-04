package com.learn.jedis.publishAndSub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * 生产者
 */
@Slf4j
public class PublishTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.72.128", 6379);
        jedis.publish("news-music", "JayZhou");
        jedis.publish("news-games", "JayZhouPlayGames");
        log.info("消息发送完毕");
    }
}
