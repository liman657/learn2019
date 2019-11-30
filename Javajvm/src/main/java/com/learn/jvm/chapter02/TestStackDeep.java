package com.learn.jvm.chapter02;

/**
 * autor:liman
 * createtime:2019/11/24
 * comment:测试java的栈的深度
 * -Xss——指定虚拟机栈的深度
 * 例如：-Xss128K
 */
public class TestStackDeep {

    private static int count = 0;
    private static void recursion() throws Exception{
        count++;
        recursion();
    }

    public static void main(String[] args) {
        try{
            recursion();
        }catch (Exception e){
            System.out.println("deep of calling = "+count);
            e.printStackTrace();
        }
    }

}
