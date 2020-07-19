package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * autor:liman
 * createtime:2020/7/18
 * comment:
 * Given two arrays of integers nums and index. Your task is to create target array under the following rules:
 *
 * Initially target array is empty.
 * From left to right read nums[i] and index[i], insert at index index[i] the value nums[i] in target array.
 * Repeat the previous step until there are no elements to read in nums and index.
 * Return the target array.
 *
 * It is guaranteed that the insertion operations will be valid.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [0,1,2,3,4], index = [0,1,2,2,1]
 * Output: [0,4,1,3,2]
 * Explanation:
 * nums       index     target
 * 0            0        [0]
 * 1            1        [0,1]
 * 2            2        [0,1,2]
 * 3            2        [0,1,3,2]
 * 4            1        [0,4,1,3,2]
 * Example 2:
 *
 * Input: nums = [1,2,3,4,0], index = [0,1,2,3,0]
 * Output: [0,1,2,3,4]
 * Explanation:
 * nums       index     target
 * 1            0        [1]
 * 2            1        [1,2]
 * 3            2        [1,2,3]
 * 4            3        [1,2,3,4]
 * 0            0        [0,1,2,3,4]
 * Example 3:
 *
 * Input: nums = [1], index = [0]
 * Output: [1]
 *
 *
 * Constraints:
 *
 * 1 <= nums.length, index.length <= 100
 * nums.length == index.length
 * 0 <= nums[i] <= 100
 * 0 <= index[i] <= i
 */
@Slf4j
public class Title1389 {

    public static void main(String[] args) {
        int[] nums = {0,1,2,3,4};
        int[] index = {0,1,2,2,1};
        int[] targetArray = createTargetArray(nums, index);
        log.info("result:{}",targetArray);
    }

    public static int[] createTargetArray(int[] nums, int[] index) {
        ArrayList<Integer> arraysList = new ArrayList(nums.length);
        int[] result = new int[nums.length];
        for(int i=0;i<nums.length;i++){
            arraysList.add(index[i],nums[i]);
        }

        for(int i = 0;i<nums.length;i++){
            result[i] = arraysList.get(i);
        }
        return result;
    }

}
