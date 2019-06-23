package com.learn.designModel.ChainOfResponsibilityPattern;

/**
 * autor:liman
 * createtime:2019/6/23
 * comment:
 */
public class PrintProcessor implements IRequestProcessor {
    @Override
    public void processor(Request request) {
        System.out.println("print request data : " + request.getName());
    }
}
