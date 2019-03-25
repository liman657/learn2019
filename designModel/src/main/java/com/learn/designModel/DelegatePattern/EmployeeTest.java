package com.learn.designModel.DelegatePattern;

/**
 * autor:liman
 * comment:
 */
public class EmployeeTest implements IEmployee{
    @Override
    public void doWork() {
        System.out.println("I'm a tester,I'm testing");
    }
}
