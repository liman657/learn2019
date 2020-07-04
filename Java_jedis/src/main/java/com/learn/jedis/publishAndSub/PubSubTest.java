package com.learn.jedis.publishAndSub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * 消费者
 */
@Slf4j
public class PubSubTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.72.128", 6379);
        final MyListener listener = new MyListener();
        // 使用模式匹配的方式设置频道
        // 会阻塞
        jedis.psubscribe(listener, new String[]{"news-*"});//这里会阻塞，下一行日志永远不会被打印
        log.info("一轮消息接受完毕");
    }
}
