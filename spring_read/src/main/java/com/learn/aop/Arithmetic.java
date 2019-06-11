package com.learn.aop;

/**
 * autor:liman
 * createtime:2019/6/9
 * comment:
 */
public class Arithmetic {
    //add
    public int add(int d,int e) {
        System.out.println("add method END!");
        System.out.println();
        return d+e;
    }

    //subtraction
    public int sub(int a,int b) {
        System.out.println("sub method END!");
        System.out.println();
        return a-b;
    }

    //multiplicative
    public int mul(int a,int b) {
        System.out.println("mul method END!");
        System.out.println();
        return a*b;
    }
    //division
    public int div(int a,int b) {
        System.out.println("div method END!");
        System.out.println();
        return a/b;
    }
}