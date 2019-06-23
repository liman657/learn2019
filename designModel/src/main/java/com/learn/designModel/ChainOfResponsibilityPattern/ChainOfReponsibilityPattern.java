package com.learn.designModel.ChainOfResponsibilityPattern;

/**
 * autor:liman
 * createtime:2019/6/23
 * comment:
 */
public class ChainOfReponsibilityPattern {

    private IRequestProcessor requestProcessor;

    /**
     * 真个处理链的构造
     */
    public void setUp(){
        //逆序构造处理链
        PrintProcessor printProcessor = new PrintProcessor();
        SaveProcessor saveProcessor = new SaveProcessor(printProcessor);
        CheckProcessor checkProcessor = new CheckProcessor(saveProcessor);
        requestProcessor = checkProcessor;
    }

    /**
     * 处理客户端的请求
     * @param request
     */
    public void print(Request request){
        requestProcessor.processor(request);
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) {
        Request request = new Request();
        request.setName("this is my data");

        ChainOfReponsibilityPattern dealRequest = new ChainOfReponsibilityPattern();
        dealRequest.setUp();
        dealRequest.print(request);
    }

}
