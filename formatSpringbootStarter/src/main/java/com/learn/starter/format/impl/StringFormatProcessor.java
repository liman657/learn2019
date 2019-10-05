package com.learn.starter.format.impl;

import com.learn.starter.format.FormatProcessor;

import java.util.Objects;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
public class StringFormatProcessor implements FormatProcessor {
    public <T> String format(T Obj) {
        return "String format result:"+Objects.toString(Obj);
    }
}
