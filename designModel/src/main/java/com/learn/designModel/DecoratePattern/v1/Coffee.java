package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * comment:基础的咖啡类，白开水+咖啡豆，售价20
 */
public class Coffee {

    public String getMaterial(){
        return "咖啡豆,"+"白开水";
    }

    public int getPrice(){
        return 20;
    }

}
