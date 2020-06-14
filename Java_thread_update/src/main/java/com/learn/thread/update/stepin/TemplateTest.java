package com.learn.thread.update.stepin;

/**
 * autor:liman
 * createtime:2020/6/3
 * comment:
 */
public class TemplateTest {
    public static void main(String[] args) {
        AbstractTemplate template = new TemplateOne();
        template.printHelloMessage("liman");

        AbstractTemplate templateTwo = new TemplateTwo();
        templateTwo.printHelloMessage("liman");
    }
}
