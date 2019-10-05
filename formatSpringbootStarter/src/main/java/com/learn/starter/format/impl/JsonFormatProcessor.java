package com.learn.starter.format.impl;

import com.learn.starter.format.FormatProcessor;
import com.alibaba.fastjson.JSON;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
public class JsonFormatProcessor implements FormatProcessor {
    public <T> String format(T Obj) {
        return "json format result:"+JSON.toJSONString(Obj);
    }
}
