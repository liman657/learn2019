package com.learn.jedis.datatype;

import com.learn.jedis.util.JedisUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:
 */
@Slf4j
public class StringTest {

    public static void main(String[] args) {
        JedisUtil.getJedisUtil().set("test", "2673");
        // JedisUtil.getJedisUtil().incr("qingshan");

        String test = JedisUtil.getJedisUtil().get("test");
        log.info("simple string : {}",test);
    }
}
