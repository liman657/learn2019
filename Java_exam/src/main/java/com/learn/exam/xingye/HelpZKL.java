package com.learn.exam.xingye;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * autor:liman
 * createtime:2020/7/21
 * comment:
 */
@Slf4j
public class HelpZKL {

    // 单个整数对称
    private static List singles = Arrays.asList(0, 8);

    public static void main(String[] args) {
        int out = -1;
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            try {
                // 输入数据
                int intput = sc.nextInt();
                String inputStr = String.valueOf(intput);

                char[] inputChar = inputStr.toCharArray();
                // 单个对称数据
                if (singles.contains(intput)) {
                    out = intput;
                } else {
                    // 基数情况（除0外），中间位置必须是0,1,8 偶数的话必须每个char都为对称一样的 如：4334
                    if (inputStr.length() % 2 == 0) {
                        for (int i = 0; i < inputChar.length; i++) {
                            int high = i;
                            int low = inputChar.length - 1 - i;
                            if (inputChar[i] == inputChar[inputChar.length - 1 - i]) {
                                continue;
                            } else {
                                if (inputChar[high] < inputChar[low]) {
                                    if (inputChar[low] < '7') {
                                        inputChar[low] = inputChar[high];
                                    } else {
                                        inputChar[high] = (char) (inputChar[high] + 1);
                                        inputChar[low] = inputChar[high];
                                    }
                                } else {
                                    if (inputChar[high] - inputChar[low] > 5) {
                                        inputChar[high] = (char) (inputChar[high] - 1);
                                    }
                                    inputChar[low] = inputChar[high];
                                }
                            }
                        }
                        out = Integer.parseInt(String.valueOf(inputChar));
                    } else {
                        // 中间值必须为0,1,8
                        int mid = (inputChar.length - 1) / 2;
                        char midChar = inputChar[mid];
                        if (!singles.contains(inputChar[mid])) {
                            if (midChar == '4') {
                                inputChar[mid] = '0'; // 或者8
                            }
                            if (midChar > '4') {
                                inputChar[mid] = '8';
                            } else {
                                inputChar[mid] = '0';
                            }
                        }
                        for (int i = 0; i < inputChar.length; i++) {
                            int high = i;
                            int low = inputChar.length - 1 - i;
                            if (inputChar[high] == inputChar[low]) {
                                continue;
                            } else {
                                if (inputChar[high] > inputChar[low]) {
                                    inputChar[low] = inputChar[high];
                                } else {
                                    inputChar[low] = inputChar[high];
                                }
                            }
                        }
                        out = Integer.parseInt(String.valueOf(inputChar));
                    }
                }
                System.out.println(out);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR");
            }
        }
    }
}
