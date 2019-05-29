package com.learn.springioc.framework.webmvc.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * autor:liman
 * createtime:2019/5/23
 * comment: 模拟的HandlerMapping
 */
public class SelfHandlerMapping {

    private Object controller;  //方法对应的实例
    private Method method;  //保存映射的方法
    private Pattern pattern;    //URL的正则匹配

    public SelfHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
