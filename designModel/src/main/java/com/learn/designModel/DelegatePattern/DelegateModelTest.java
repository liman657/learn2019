package com.learn.designModel.DelegatePattern;

/**
 * autor:liman
 * comment:
 */
public class DelegateModelTest {

    public static void main(String[] args) {
        Boss boss = new Boss();
        Leader leader = new Leader();
        boss.command("code",leader);
        boss.command("test",leader);
    }

}
