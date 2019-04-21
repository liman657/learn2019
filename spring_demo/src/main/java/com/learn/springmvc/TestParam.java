package com.learn.springmvc;

import com.learn.springmvc.Annotation.SelfRequestParam;

/**
 * autor:liman
 * comment:
 */
public class TestParam {

    public String annoTest(@SelfRequestParam("test") String param){
        System.out.println(param);
        return param;
    }

}
