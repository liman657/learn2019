package com.learn.exam.leetcode.easy;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/15
 * comment:
 * Given a valid (IPv4) IP address, return a defanged version of that IP address.
 *
 * A defanged IP address replaces every period "." with "[.]".
 *
 *
 *
 * Example 1:
 *
 * Input: address = "1.1.1.1"
 * Output: "1[.]1[.]1[.]1"
 * Example 2:
 *
 * Input: address = "255.100.50.0"
 * Output: "255[.]100[.]50[.]0"
 *
 *
 * Constraints:
 *
 * The given address is a valid IPv4 address.
 */
@Slf4j
public class Title1108 {

    public static void main(String[] args) {
        String address = "1.1.1.1";
        String result = defangIPaddr(address);
        log.info("result:{}",result);
    }

    public static String defangIPaddr(String address) {
        return address.replace(".","[.]");
    }
}
