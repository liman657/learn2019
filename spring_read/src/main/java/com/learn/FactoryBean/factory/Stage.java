package com.learn.FactoryBean.factory;

/**
 * autor:liman
 * createtime:2019/6/5
 * comment:
 */
public class Stage {

    private String stageName;

//    public void setName(String stageName) {
//        this.stageName = stageName;
//    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public void Show(){
        System.out.println(stageName+"舞台搭建完成");
    }
}
