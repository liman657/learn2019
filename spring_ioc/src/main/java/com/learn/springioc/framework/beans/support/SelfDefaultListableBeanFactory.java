package com.learn.springioc.framework.beans.support;

import com.learn.springioc.framework.beans.config.SelfBeanDefinition;
import com.learn.springioc.framework.context.support.SelfAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment:
 */
public class SelfDefaultListableBeanFactory extends SelfAbstractApplicationContext {

    public final Map<String,SelfBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,SelfBeanDefinition>();

}
