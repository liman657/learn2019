package com.learn.jvm.chapter07;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment:
 * 1.6以后，常量池移到了堆上
 */
public class StringInternOOM {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        int i = 0;
        while(true){
            System.out.println(i);
            list.add(String.valueOf(i++).intern());
        }
    }

}
