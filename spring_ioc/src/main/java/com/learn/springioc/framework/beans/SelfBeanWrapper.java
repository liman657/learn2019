package com.learn.springioc.framework.beans;

/**
 * autor:liman
 * createtime:2019/5/11
 * comment:
 */
public class SelfBeanWrapper {

    private Object wrappedInstance;//真正bean对象的实体
    private Class<?> wrappedClass;//真正bean对象的class实例

    public SelfBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return this.wrappedInstance.getClass();
    }
}
