package com.learn.designModel.ChainOfResponsibilityPattern;

/**
 * autor:liman
 * createtime:2019/6/23
 * comment:
 */
public class ChainOfReponsibilityPattern {

    private IRequestProcessor requestProcessor;

    public void setUp(){
        PrintProcessor printProcessor = new PrintProcessor();
        SaveProcessor saveProcessor = new SaveProcessor(printProcessor);
        CheckProcessor checkProcessor = new CheckProcessor(saveProcessor);
        requestProcessor = checkProcessor;
    }

    public void print(Request request){
        requestProcessor.processor(request);
    }


    public static void main(String[] args) {
        Request request = new Request();
        request.setName("this is my data");

        ChainOfReponsibilityPattern dealRequest = new ChainOfReponsibilityPattern();
        dealRequest.setUp();
        dealRequest.print(request);

    }

}
