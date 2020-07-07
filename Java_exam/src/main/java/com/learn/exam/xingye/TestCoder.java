package com.learn.exam.xingye;

import java.util.Scanner;

/**
 * autor:liman
 * createtime:2020/7/2
 * comment:
 */
public class TestCoder {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int ans = 0, x;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                x = sc.nextInt();
                ans += x;
            }
        }
        System.out.println(ans);
    }
}
