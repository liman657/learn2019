package com.learn.springioc.framework.beans;

/**
 * autor:liman
 * comment:IOC 的顶层设计
 */
public interface SelfBeanFactory {

    Object getBean(String beanName);

}
