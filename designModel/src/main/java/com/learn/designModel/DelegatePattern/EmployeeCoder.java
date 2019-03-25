package com.learn.designModel.DelegatePattern;


/**
 * autor:liman
 * comment:
 */
public class EmployeeCoder implements IEmployee {
    @Override
    public void doWork() {
        System.out.println("I'm a coder ,I'm coding");
    }
}
