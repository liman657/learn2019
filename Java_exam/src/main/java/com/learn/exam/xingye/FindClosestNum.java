package com.learn.exam.xingye;

import java.util.Scanner;

/**
 * autor:liman
 * createtime:2020/10/20
 * comment:
 */
public class FindClosestNum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        char[] chars = str.toCharArray();
        int len = str.length();
        for (int i = 0; i < len / 2; i++) {
            chars[len - 1 - i] = chars[i];
        }
        System.out.println(String.valueOf(chars));
    }

}
