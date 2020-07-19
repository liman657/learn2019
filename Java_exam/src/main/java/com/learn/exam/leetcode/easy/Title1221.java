package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

/**
 * autor:liman
 * createtime:2020/7/18
 * comment:明显需要用栈
 * 1221. Split a String in Balanced Strings
 * Easy
 * <p>
 * 533
 * <p>
 * 363
 * <p>
 * Add to List
 * <p>
 * Share
 * Balanced strings are those who have equal quantity of 'L' and 'R' characters.
 * <p>
 * Given a balanced string s split it in the maximum amount of balanced strings.
 * <p>
 * Return the maximum amount of splitted balanced strings.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: s = "RLRRLLRLRL"
 * Output: 4
 * Explanation: s can be split into "RL", "RRLL", "RL", "RL", each substring contains same number of 'L' and 'R'.
 * Example 2:
 * <p>
 * Input: s = "RLLLLRRRLR"
 * Output: 3
 * Explanation: s can be split into "RL", "LLLRRR", "LR", each substring contains same number of 'L' and 'R'.
 * Example 3:
 * <p>
 * Input: s = "LLLLRRRR"
 * Output: 1
 * Explanation: s can be split into "LLLLRRRR".
 * Example 4:
 * <p>
 * Input: s = "RLRRRLLRLL"
 * Output: 2
 * Explanation: s can be split into "RL", "RRRLLRLL", since each substring contains an equal number of 'L' and 'R'
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= s.length <= 1000
 * s[i] = 'L' or 'R'
 */
@Slf4j
public class Title1221 {

    public static void main(String[] args) {
//        int count = balancedStringSplit("RLRRRLLRLL");
        int countBetter = balancedStringSplitBetter("RLRRLLRLRL");
        log.info("result:{}", countBetter);
    }

    public static int balancedStringSplit(String s) {
        Stack<Character> charStack = new Stack<>();
        Stack<Character> calculateStack = new Stack<>();
        char[] chars = s.toCharArray();
        int count = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            charStack.push(chars[i]);
        }
        calculateStack.push(charStack.pop());
        while (!charStack.isEmpty()) {
            Character target = calculateStack.peek();//取出顶部元素比较
            Character top = charStack.pop();
            if (top != target) {
                calculateStack.pop();
                if (calculateStack.isEmpty()) {
                    count++;
                    //将下一个元素入栈
                    if (!charStack.isEmpty()) {
                        calculateStack.push(charStack.pop());
                    }
                }
            } else if (top == target) {
                calculateStack.push(top);
            }
        }
        return count;
    }

    public static int balancedStringSplitBetter(String s) {
        Stack<Character> charStack = new Stack<>();
        int count = 0;
        int index = 0;
        char[] charArray = s.toCharArray();
        charStack.push(charArray[0]);
        while (index < charArray.length-1) {
            Character target = charStack.peek();
            Character top = charStack.push(charArray[++index]);
            if (top != target) {
                charStack.pop();
                charStack.pop();
                if (charStack.isEmpty()) {
                    count++;
                }
                //入栈下一个元素
                charStack.push(charArray[++index]);
            }
        }
        return count;
    }
}
