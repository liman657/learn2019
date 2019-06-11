package com.learn.FactoryBean;

/**
 * autor:liman
 * createtime:2019/6/5
 * comment:
 */
public class Dancer {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void Show(){
        stage.CreateStageSuccess();
        System.out.println("somebody dancer 上台");
    }
}
