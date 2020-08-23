package com.learn.exam.xingye;

import java.util.Scanner;

/**
 * autor:liman
 * createtime:2020/7/21
 * comment:
 */
public class FinalTest {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            int num = scanner.nextInt();

            //输入对称数字
            if (isSymmetry(String.valueOf(num))) {
                System.out.println(num);
            } else {
                int maxNumber = findMaxNumber(num);
                int minNumber = findMinNumber(num);

                if (minNumber == -1) {
                    System.out.println(maxNumber);
                } else {
                    if (Math.abs(maxNumber - num) == Math.abs(minNumber - num)) {
                        System.out.println(minNumber + "," + maxNumber);
                    } else {
                        if (Math.abs(maxNumber - num) > Math.abs(minNumber - num)) {
                            System.out.println(minNumber);
                        } else {
                            System.out.println(maxNumber);
                        }
                    }
                }
            }
        }catch (Exception e){
            System.out.println("ERROR");
        }
    }

    /**
     * 给定一个正整数字符串，判断其是否为对称
     *
     * @param str
     * @return
     */
    private static Boolean isSymmetry(String str) {
        int len = str.length();
        //数字8不是对称正整数
        if (len == 1) {
            return false;
        }
        for (int i = 0; i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取比num大的距离最近的对称整数
     *
     * @param num
     * @return
     */
    private static int findMaxNumber(int num) {
        if (isSymmetry(String.valueOf(num))) {
            return num;
        } else {
            return findMaxNumber(++num);
        }
    }

    /**
     * 获取比num小的距离最近的对称整数
     *
     * @param num
     * @return
     */
    private static int findMinNumber(int num) {
        //未找到对称正整数字符串
        if (num <= 0) {
            return -1;
        }
        if (isSymmetry(String.valueOf(num))) {
            return num;
        } else {
            return findMinNumber(--num);
        }
    }
}
