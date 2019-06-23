package com.learn.designModel.ChainOfResponsibilityPattern;

/**
 * autor:liman
 * createtime:2019/6/23
 * comment:
 */
public class CheckProcessor implements IRequestProcessor {

    private IRequestProcessor nextProcessor;

    public CheckProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void processor(Request request) {
        System.out.println("check request data:"+request.getName());
    }
}