package com.learn.designModel.DelegatePattern;

/**
 * autor:liman
 * comment:
 */
public class Boss {

    public void command(String command,Leader leader){
        leader.doSomething(command);
    }

}
