package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2020/7/20
 * comment:
 * Given an array nums of integers, return how many of them contain an even number of digits.
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [12,345,2,6,7896]
 * Output: 2
 * Explanation:
 * 12 contains 2 digits (even number of digits).
 * 345 contains 3 digits (odd number of digits).
 * 2 contains 1 digit (odd number of digits).
 * 6 contains 1 digit (odd number of digits).
 * 7896 contains 4 digits (even number of digits).
 * Therefore only 12 and 7896 contain an even number of digits.
 * Example 2:
 * <p>
 * Input: nums = [555,901,482,1771]
 * Output: 1
 * Explanation:
 * Only 1771 contains an even number of digits.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= nums.length <= 500
 * 1 <= nums[i] <= 10^5
 */
@Slf4j
public class Title1295 {

    public static void main(String[] args) {

        int[] nums = {12,345,2,6,7896};
        int numbers = findNumbers(nums);
        log.info("result:{}",numbers);

    }

    public static int findNumbers(int[] nums) {
        IntPredicate isEvenNumCount = new IntPredicate() {
            @Override
            public boolean test(int value) {
                if ((value > 9 && value < 100) || (value > 999 && value < 10000) || (value == 100000)) {
                    return true;
                }
                return false;
            }
        };
        long count = Arrays.stream(nums).filter(isEvenNumCount).count();
        return (int) count;
    }

}
