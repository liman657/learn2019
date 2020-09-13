package com.learn.test.netty.randome;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Random;

/**
 * autor:liman
 * createtime:2020/9/13
 * comment:
 */
@Slf4j
public class FoundationTest {

    @Test
    public void testRandombound(){
        for(int i=0;i<100;i++) {
            int index = new Random().nextInt(2);
            System.out.println(index);
        }
    }

}
