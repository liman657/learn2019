package com.learn.exam.leetcode.easy;

/**
 * autor:liman
 * createtime:2020/7/8
 * comment:
 * Determine whether an integer is a palindrome. An integer is a palindrome when it reads the same backward as forward.
 *
 * Example 1:
 *
 * Input: 121
 * Output: true
 * Example 2:
 *
 * Input: -121
 * Output: false
 * Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
 * Example 3:
 *
 * Input: 10
 * Output: false
 * Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
 *
 * Follow up:
 *
 * Coud you solve it without converting the integer to a string?
 */
public class Title009 {

    public static void main(String[] args) {
        int num = -121;
        System.out.println(isPalindrome(num));
    }

    public static boolean isPalindrome(int x){
        int reverse = reverse(Math.abs(x));
        return reverse == x;
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
