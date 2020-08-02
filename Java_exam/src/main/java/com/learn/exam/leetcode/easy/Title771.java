package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/28
 * comment:
 * You're given strings J representing the types of stones that are jewels, and S representing the stones you have.  Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels.
 *
 * The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters are case sensitive, so "a" is considered a different type of stone from "A".
 *
 * Example 1:
 *
 * Input: J = "aA", S = "aAAbbbb"
 * Output: 3
 * Example 2:
 *
 * Input: J = "z", S = "ZZ"
 * Output: 0
 * Note:
 *
 * S and J will consist of letters and have length at most 50.
 * The characters in J are distinct.
 */
@Slf4j
public class Title771 {

    public static void main(String[] args) {
        int result = numJewelsInStones("aA", "aAAbbbb");
        log.info("result:{}",result);
    }

    public static int numJewelsInStones(String J, String S) {
        Set<Character> charKeys = new HashSet<>();
        char[] keyChar = J.toCharArray();
        char[] sourceChar = S.toCharArray();
        int result = 0;
        for(char ch:keyChar){
            charKeys.add(ch);
        }

        for(char ch:sourceChar){
            if(charKeys.contains(ch)){
                result++;
            }
        }
        return result;
    }

}
