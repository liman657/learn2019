package com.learn.designModel.PrototypePattern.deepClone;

/**
 * autor:liman
 * comment: 深克隆测试类
 */
public class DeepCloneTest {

    public static void main(String[] args) {
        SunWuKong sunWuKong = new SunWuKong();
        try{
            SunWuKong cloneSunWuKong = (SunWuKong) sunWuKong.clone();
            System.out.println(cloneSunWuKong.getGoldenCudgel() == sunWuKong.getGoldenCudgel());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}