package com.learn.exam.leetcode.easy;

/**
 * autor:liman
 * createtime:2020/7/8
 * comment:
 * Given a 32-bit signed integer, reverse digits of an integer.
 * <p>
 * Example 1:
 * <p>
 * Input: 123
 * Output: 321
 * Example 2:
 * <p>
 * Input: -123
 * Output: -321
 * Example 3:
 * <p>
 * Input: 120
 * Output: 21
 * Note:
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1].
 * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
 */
public class Title007 {

    public static void main(String[] args) {
//        int result = reverse(1534236469);
        int result = reverse(-121);
        System.out.println(result);
    }

    public static int reverse(int x) {
        int byTen = 0;
        long result = 0;
        while (x != 0) {
            result = result * 10 + (x % 10);
            byTen = x / 10;
            x = byTen;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE)//避免溢出造成结果不正确
            result = 0;
        return (int) result;
    }
}
