package com.learn.foundation;

/**
 * autor:liman
 * comment: 北京妞的神奇题目
 */
public class Play {

    public static void main(String[] args) {
        int result = 707829217;
        int length = result/2;
        String num = "";

        for(int i=2;i<length;i++){
            if(isSushu(i)){
                if(result%i == 0){
                    System.out.println(result/i+":"+i);
                    num = String.valueOf(result/i+""+i);
                    break;
                }
            }
        }


        System.out.println(count3Time(Integer.valueOf(num)));

    }

    public static int count3Time(int num){
        int count = 0;
        for(int i=1;i<=num;i=i+2){
            char[] chars = String.valueOf(i).toCharArray();
//            System.out.println(String.valueOf(i));
            for(int j=0;j<chars.length;j++){
                if(chars[j]=='3'){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 判断素数
     * @param num
     * @return
     */
    public static boolean isSushu(int num){
        if(num == 1 || num ==2){
            return true;
        }
        int len = num/2;
        for(int i=2;i<=len;i++){
            if(num%i == 0){
                return false;
            }
        }
        return true;
    }

}
