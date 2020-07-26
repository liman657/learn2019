package com.learn.exam.leetcode.easy;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/23
 * comment:
 */
@Slf4j
@Data
public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    public ListNode createLiseNode(int[] nums){
        ListNode head = null;
        ListNode index = head;
        for(int i=0;i<nums.length;i++){
            ListNode node = new ListNode();
            node.val = nums[i];
            index = node;
            index = index.next;
        }
        return head;
    }

    public static void printListNode(ListNode head){
        ListNode index = head;
        while(index!=null){
            System.out.println(index.val);
            index = index.next;
        }
    }
}
