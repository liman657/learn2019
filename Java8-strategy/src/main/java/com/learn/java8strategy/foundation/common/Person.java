package com.learn.java8strategy.foundation.common;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

/**
 * autor:liman
 * createtime:2020/12/18
 * comment:Java8 第一章简单的demo实体
 */
@Data
public class Person {

    private String userId;
    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(Person person){
        this.name = person.name;
    }


}
