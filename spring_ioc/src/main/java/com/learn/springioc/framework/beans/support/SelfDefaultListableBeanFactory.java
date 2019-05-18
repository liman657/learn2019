package com.learn.springioc.framework.beans.support;

import com.learn.springioc.framework.beans.config.SelfBeanDefinition;
import com.learn.springioc.framework.context.support.SelfAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment:spring源码中就是在DefaultListableBeanFactory中维护了IOC的数据结构
 */
public class SelfDefaultListableBeanFactory extends SelfAbstractApplicationContext {

    /**
     * 这个不是真正的IOC容器，真正的IOC容器是去保存BeanWrapper
     */
    public final Map<String,SelfBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,SelfBeanDefinition>();

}
