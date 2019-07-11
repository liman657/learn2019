package com.learn.chainOfResponsibility;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment:
 */
public class Request {

    private String name;

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
