package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/18
 * comment:
 * We are given a subMenuList nums of integers representing a subMenuList compressed with run-length encoding.
 *
 * Consider each adjacent pair of elements [freq, val] = [nums[2*i], nums[2*i+1]] (with i >= 0).  For each such pair, there are freq elements with value val concatenated in a sublist. Concatenate all the sublists from left to right to generate the decompressed subMenuList.
 *
 * Return the decompressed subMenuList.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: [2,4,4,4]
 * Explanation: The first pair [1,2] means we have freq = 1 and val = 2 so we generate the array [2].
 * The second pair [3,4] means we have freq = 3 and val = 4 so we generate [4,4,4].
 * At the end the concatenation [2] + [4,4,4] is [2,4,4,4].
 * Example 2:
 *
 * Input: nums = [1,1,2,3]
 * Output: [1,3,3]
 *
 *
 * Constraints:
 *
 * 2 <= nums.length <= 100
 * nums.length % 2 == 0
 * 1 <= nums[i] <= 100
 */
@Slf4j
public class Title1313 {

    public static void main(String[] args) {

        int[] nums = {1,1,2,3};
        int[] result = decompressRLElist(nums);
        log.info("result:{}",result);

    }

    public static int[] decompressRLElist(int[] nums) {
        int resultLength = 0;
        for(int i=0;i<nums.length;i++){
            if(i%2==0){
                resultLength+=nums[i];
            }
        }
        int[] result = new int[resultLength];
        int index = 0,count=0;
        for(int i=0;i<nums.length;i++){
            if(i%2!=0){
                count = nums[i-1];
                while(count>0){
                    result[index++] = nums[i];
                    count--;
                }
            }
        }
        return result;
    }
}
