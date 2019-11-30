package com.learn.jvm;

import java.io.File;

/**
 * autor:liman
 * createtime:2019/11/19
 * comment:
 * 数据，指令，控制
 */
public class RuntimeDataArea {

    //静态 常量
    private final int i = 0;
    private static int k = 0;

    //成员变量
    private Object obj = new Object();
    private int sss = 0;
    
    public void methodOne(int i){
        int j=0;
        int sum = i+j;
        Object abc = obj;
        long start = System.currentTimeMillis();
        methodTwo(2);
        return;
    }

    public void methodTwo(int i) {
        File file = new File("");
        int j=6;
        int sum = i+j;
    }

    public static void main(String[] args) {
        for(String s:args){
            System.out.println("params:"+s);
        }

    }
}
