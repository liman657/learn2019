package com.learn.jvm.chapter11;

/**
 * autor:liman
 * createtime:2019/12/4
 * comment:数组局部变量表压栈的实例
 */
public class ArrayStackDemo {

    /**
     * 局部变量压栈的指令
     *
     * @param cs
     * @param s
     */
    public void print3(char[] cs, short[] s) {
        System.out.println(s[2]);
        System.out.println(cs[0]);
    }

    /**
     * 出栈装入局部变量表的指令
     *
     * @param cs
     * @param s
     */
    public void print4(char[] cs, char[] s) {
        int i, j, k, x;
        x = 99;
        s[0] = 77;
    }

    /**
     * 通用型操作指令
     *
     * @param i nop 什么都不做，dup——duplicate复制栈顶的元素并压栈，pop——将一个元素出栈，并直接丢弃
     */
    public void print5(int i) {
        Object obj = new Object();
        obj.toString();
    }

    /**
     * 类型转换指令
     *
     * @param i
     */
    public void print6(int i) {
        long l = i;
        float f = l;
        int j = (int) l;
    }

    /**
     * 并没有byte转换成int的指令，虚拟机底层已经将byte作为int类型来处理了
     *
     * @param i
     */
    public void print7(byte i) {
        int k = i;
        long l = i;
    }

    /**
     * 运算指令
     *
     * @param j
     */
    public void print8(int j) {
        int i = 123;
        i = i + 10;   //书上说这个地方用的是自增，但是1.8看来不是这样子
        i++;
    }

    /**
     * 对象/数组创建指令
     */
    public void print9() {
        int[] intarray = new int[10];
        Object[] objarray = new Object[10];
        int[][] mintarray = new int[10][10];
    }

    /**
     * 对象/数组字段访问指令
     * [get/put]field 操作实例对象的字段
     * [get/put]static 操作类的静态字段
     */
    public void print10() {
        System.out.println("hello"); //System.out——getstatic
    }

    /**
     * 类型检查
     * checkcast instanceof，接受一个操作数，并判断栈顶元素是否可以转为该操作数给定的类型
     */
    public String print11(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        } else {
            return null;
        }
    }

    /**
     * 比较指令
     */
    public void print12() {
        float f1 = 9;
        float f2 = 10;
        System.out.println(f1 > f2);//fcmpl

        short s1 = 9;
        byte b1 = 10;
        System.out.println(s1 > b1);//if_icmple

        Object o1 = new Object();
        Object o2 = new Object();
        System.out.println(o1 == o2); //if_acmpeq
        System.out.println(o1 != o2); //if_acmpne
    }

}
