package com.learn.jvm.chapter07;

/**
 * autor:liman
 * createtime:2019/12/3
 * comment:JVM对String的优化
 * 三个基本特点：1.不变性，2.针对常量池的额优化，3.类的final定义
 * string.intern()返回字符串在常量池中的引用
 * String类型 针对常量池的优化，当两个String对象拥有相同的值时，他们只引用常量池中的同一个拷贝，当同一个字符串反复出现的时候，这可以大大节省空间
 *
 */
public class StringJVMOOM {

    public static void main(String[] args) {
        String str1 = new String("abc");
        String str2 = new String("abc");

        System.out.println(str1 == str2);
        System.out.println(str1 == str2.intern());
        System.out.println("abc"==str2.intern());
        System.out.println(str1.intern()==str2.intern());
    }


}
