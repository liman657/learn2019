package com.learn.exam.xingye;

import org.omg.CORBA.INTERNAL;

import java.util.Scanner;

/**
 * autor:liman
 * createtime:2021/3/4
 * comment:
 */
public class HelpZhouYuan {


    public static void main(String[] args) {
        String result = "";
        Scanner scanner = new Scanner(System.in);
        String inputNum = scanner.next();

        char[] numChar = inputNum.toCharArray();
        for(char ch:numChar){
            if(!Character.isDigit(ch) && numChar[0] != '-'){
                result= "INPUTERROR";
                System.out.println(result);
                System.exit(0);
            }
        }

        long num = Long.valueOf(inputNum);
        if(num > 65535 || num < Integer.MIN_VALUE){
            result = "NODATA";
            System.out.println(result);
            System.exit(0);
        }

        StringBuffer resultBuffer = new StringBuffer("");

        for(int i=15;i>=0;i--){
            String iNum = String.valueOf(num>>i&1);//右移i位
            resultBuffer.append(iNum);
        }

        resultBuffer.append(",");
        num = Integer.valueOf(inputNum);
        for(int i=3;i>=0;i--) {
            String test = String.format("%X",(num>>(4*i))&15);
            resultBuffer.append(test);//15二进制下为1111
        }
        result = resultBuffer.toString();
        System.out.println(result);
    }

}
