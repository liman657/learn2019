package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2020/7/9
 * comment: 找到字符串最小前缀
 * Write a function to find the longest common prefix string amongst an array of strings.
 * <p>
 * If there is no common prefix, return an empty string "".
 * <p>
 * Example 1:
 * <p>
 * Input: ["flower","flow","flight"]
 * Output: "fl"
 * Example 2:
 * <p>
 * Input: ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * Note:
 * <p>
 * All given inputs are in lowercase letters a-z.
 */
@Slf4j
public class Title014 {

    public static void main(String[] args) {

        String[] strs = {"dog", "racecar", "car"};
        String[] strs2 = {"flower", "flow", "flight"};
        String[] strs3 = {"c", "c", "c"};
        longestCommonPrefix(strs);
        longestCommonPrefix(strs2);
        longestCommonPrefix(strs3);

    }

    public static String longestCommonPrefix(String[] strs) {
        String result = "";
        if(strs.length==0){
            return result;
        }

        if(strs.length==1){
            return strs[0];
        }
        int finalLength = 0;
        int strNum = strs.length;
        char[] charNums = new char[strNum];
        int shortestLength = findShortestLength(strs);
        for (int i = 0; i < shortestLength; i++) {
            for (int j = 0; j < strNum; j++) {
                charNums[j] = strs[j].charAt(i);
            }
            if (!isSameChar(charNums)) {
                finalLength = i;
                break;
            }
        }
        result = strs[0].substring(0, finalLength);
        log.info("{}", result);
        return result;

    }

    public static int findShortestLength(String[] strs) {
        if (strs.length > 0) {
            int length = strs[0].length();
            for (String str : strs) {
                if (length >= str.length()) {
                    length = str.length();
                }
            }
        }
        return 0;

//        int length = 0;
//        length = Optional.ofNullable(Arrays.stream(strs).collect(Collectors.minBy(Comparator.comparingInt(String::length))).get().length()).orElse(0);
//        return length;
    }

    public static boolean isSameChar(char[] charNum) {
        for (int i = 0; i < charNum.length - 1; i++) {
            if (charNum[i] != charNum[i + 1]) {
                return false;
            }
        }
        return true;
    }
}