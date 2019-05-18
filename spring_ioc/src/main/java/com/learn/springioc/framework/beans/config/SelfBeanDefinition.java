package com.learn.springioc.framework.beans.config;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment:
 */
public class SelfBeanDefinition {

    private String beanClassName;//获得到的全路径的类名——例如：com.learn.spring.beanName.class
    private boolean lazyInit = false; //是否懒加载
    private String factoryBeanName; //真正放入容器中的类名——<bean id = ""/name="">指定的名称
    private boolean isSingleton;

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public void setSingleton(boolean singleton) {
        isSingleton = singleton;
    }
}
