package com.learn.springioc.framework.context;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment: 自动注入ioc容器的接口，如果某个类实现了这个接口，则该类会自动注入applicationContext
 * 从名称可以看出这个是装饰者模式
 */
public interface SelfApplicationContextAware {

    public void setApplicationContext(SelfApplicationContext applicationContext);

}
