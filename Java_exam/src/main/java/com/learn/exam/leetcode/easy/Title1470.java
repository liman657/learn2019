package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/15
 * comment:
 * Given the array nums consisting of 2n elements in the form [x1,x2,...,xn,y1,y2,...,yn].
 *
 * Return the array in the form [x1,y1,x2,y2,...,xn,yn].
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,5,1,3,4,7], n = 3
 * Output: [2,3,5,4,1,7]
 * Explanation: Since x1=2, x2=5, x3=1, y1=3, y2=4, y3=7 then the answer is [2,3,5,4,1,7].
 * Example 2:
 *
 * Input: nums = [1,2,3,4,4,3,2,1], n = 4
 * Output: [1,4,2,3,3,2,4,1]
 * Example 3:
 *
 * Input: nums = [1,1,2,2], n = 2
 * Output: [1,2,1,2]
 *
 *
 * Constraints:
 *
 * 1 <= n <= 500
 * nums.length == 2n
 * 1 <= nums[i] <= 10^3
 */
@Slf4j
public class Title1470 {

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,4,3,2,1};
        int[] result = shuffle(nums, 4);
        log.info("result:{}",result);
    }

    public static int[] shuffle(int[] nums, int n) {
        int left = 0;
        int right = n;
        int index = 0;
        int[] result = new int[nums.length];
        while(left<n && right<nums.length){
            result[index++] = nums[left++];
            result[index++] = nums[right++];
        }
        return result;
    }

}
