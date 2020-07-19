package com.learn.exam.leetcode.easy;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * autor:liman
 * createtime:2020/7/8
 * comment:
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 * <p>
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 * <p>
 * Example:
 * <p>
 * Given nums = [2, 7, 11, 15], target = 9,
 * <p>
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class Title001 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int target = sc.nextInt();
        int[] nums = {2, 7, 11, 15};
        //先排序，便于查找
        int[] sortedNums = Arrays.stream(nums).sorted().toArray();
        //先找到目标数值
        int[] resultTarget = new int[2];
        //最终存放的结果
        int[] result = new int[2];
        findTargetSum(sortedNums, target, resultTarget);
        findTargetNumIndex(nums, resultTarget, result);

        Arrays.stream(result).forEach(System.out::print);
    }

    /**
     * 排序之后，利用二分查找
     * @param nums
     * @param target
     * @param resultNum
     */
    public static void findTargetSum(int[] nums, int target, int[] resultNum) {
        int left = 0, right = nums.length - 1;
        int tempSum = 0;
        while (left <= right) {
            tempSum = nums[left] + nums[right];
            if (tempSum > target) {
                right--;
            }

            if (tempSum < target) {
                left++;
            }

            if (target == tempSum) {
                resultNum[0] = nums[left];
                resultNum[1] = nums[right];
                break;
            }
        }
    }

    /**
     * 找到指定数值的下标
     * @param nums
     * @param resultTarget 指定数值被寻找之后，需要标记为已经查找，题目要求不可重复
     * @param result
     */
    public static void findTargetNumIndex(int[] nums, int[] resultTarget, int[] result) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (resultTarget[0] == nums[i]) {
                left = i;
                resultTarget[0] = -65535;//已经标记了，就不能再用了
            }

            if (resultTarget[1] == nums[i] && i != left) {
                right = i;
                resultTarget[1] = -65535;//已经标记了，就不能再用了
            }
        }
        result[0] = left;
        result[1] = right;
    }


}
