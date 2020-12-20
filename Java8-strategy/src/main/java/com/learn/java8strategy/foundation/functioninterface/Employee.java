package com.learn.java8strategy.foundation.functioninterface;

/**
 * autor:liman
 * createtime:2020/12/20
 * comment:
 */
public interface Employee {

    String getFirstName();

    String getLastName();

    void convertCaffeineToCodeForMoney();

    //接口的默认实现，这个实现也向下兼容，依赖于具体的类对getFirstName和getLastName的实现

    /**
     * 接口中引入默认方法的最大好处在于：
     * 一般而言，在出现default方法之前，如果为接口添加一个新方法，就会破坏所有已经实现了这个接口的子类
     * default方法出来之后，如果添加的新方法被声明为默认方法，则所有的实现将继承新方法且依然有效。
     * 默认方法的实现与其他方法的实现并无二异。
     * @return
     */
    default String getFullName(){
        return String.format("%s %s",getFirstName(),getLastName());
    }

}
