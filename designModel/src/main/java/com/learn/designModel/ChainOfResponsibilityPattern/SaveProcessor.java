package com.learn.designModel.ChainOfResponsibilityPattern;

/**
 * autor:liman
 * createtime:2019/6/23
 * comment:
 */
public class SaveProcessor implements IRequestProcessor {

    private final IRequestProcessor nextProcessor;

    public SaveProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void processor(Request request) {
        System.out.println("save request data : "+request.getName());
        nextProcessor.processor(request);
    }
}
