package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/23
 * comment:
 * Given head which is a reference node to a singly-linked subMenuList. The value of each node in the linked subMenuList is either 0 or 1. The linked subMenuList holds the binary representation of a number.
 *
 * Return the decimal value of the number in the linked subMenuList.
 *
 *
 *
 * Example 1:
 *
 *
 * Input: head = [1,0,1]
 * Output: 5
 * Explanation: (101) in base 2 = (5) in base 10
 * Example 2:
 *
 * Input: head = [0]
 * Output: 0
 * Example 3:
 *
 * Input: head = [1]
 * Output: 1
 * Example 4:
 *
 * Input: head = [1,0,0,1,0,0,1,1,1,0,0,0,0,0,0]
 * Output: 18880
 * Example 5:
 *
 * Input: head = [0,0]
 * Output: 0
 *
 *
 * Constraints:
 *
 * The Linked List is not empty.
 * Number of nodes will not exceed 30.
 * Each node's value is either 0 or 1.
 */
@Slf4j
public class Title1290 {

    public static void main(String[] args) {

    }

    public static int getDecimalValue(ListNode head) {
        int result = 0;
        ListNode index = head.next;
        while(index.next!=null){
            result = ((result << 1) | index.val);
            index = index.next;
        }
        return result;
    }

}

//
//class ListNode {
//    int val;
//    ListNode next;
//    ListNode() {}
//    ListNode(int val) { this.val = val; }
//    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
//}
