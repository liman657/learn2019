package com.learn.fundation;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * autor:liman
 * createtime:2020/9/2
 * comment:
 */
@Slf4j
public class SplitTest {


    @Test
    public void testSplit(){
        String url = "test|url";
        String[] split = url.split("\\|");
        for(int i =0;i<split.length;i++){
            log.info(split[i]);
        }



    }

}
