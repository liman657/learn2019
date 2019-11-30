package com.learn.jvm.chapter02;

/**
 * autor:liman
 * createtime:2019/11/24
 * comment:简单的实例展示Java堆，方法区和Java栈之间的关系
 */
public class SimpleHeap {
    private int id;

    public SimpleHeap(int id) {
        this.id = id;
    }

    public void show(){
        System.out.println("my id is "+id);
    }

    public static void main(String[] args) {
        SimpleHeap simpleHeap01 = new SimpleHeap(1);
        SimpleHeap simpleHeap02 = new SimpleHeap(2);
        simpleHeap01.show();
        simpleHeap02.show();
    }
}
