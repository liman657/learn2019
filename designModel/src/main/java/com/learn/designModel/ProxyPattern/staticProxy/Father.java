package com.learn.designModel.ProxyPattern.staticProxy;

/**
 * autor:liman
 * comment:
 */
public class Father {

    private Son son;

    public Father(Son son) {
        this.son = son;
    }

    public void findLove(){
        System.out.println("父亲开始物色");
        son.seeGirl();
        System.out.println("成了！！");
    }

}
