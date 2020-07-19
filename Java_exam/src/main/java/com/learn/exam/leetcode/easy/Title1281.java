package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/18
 * comment:
 * Given an integer number n, return the difference between the product of its digits and the sum of its digits.
 *
 *
 * Example 1:
 *
 * Input: n = 234
 * Output: 15
 * Explanation:
 * Product of digits = 2 * 3 * 4 = 24
 * Sum of digits = 2 + 3 + 4 = 9
 * Result = 24 - 9 = 15
 * Example 2:
 *
 * Input: n = 4421
 * Output: 21
 * Explanation:
 * Product of digits = 4 * 4 * 2 * 1 = 32
 * Sum of digits = 4 + 4 + 2 + 1 = 11
 * Result = 32 - 11 = 21
 *
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * Accepted
 * 81,770
 * Submissions
 * 96,174
 */
@Slf4j
public class Title1281 {

    public static void main(String[] args) {
        int i = subtractProductAndSum(4421);
        log.info("result:{}",i);
    }

    public static int subtractProductAndSum(int n) {
        int result = 0;
        int product = 1;
        int sum = 0;
        while(n>0){
            int index = n % 10;
            product*=index;
            sum+=index;
            n = n/10;
        }
        result = product-sum;
        return result;
    }
}
