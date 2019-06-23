package com.learn.ChainOfResponsibility;

import jdk.internal.util.xml.impl.ReaderUTF8;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment:
 */
public class PrevProcessor extends Thread implements IRequestProcessor {

    LinkedBlockingQueue<Request> requests = new LinkedBlockingQueue<>();

    private final IRequestProcessor nextProcessor;

    private volatile boolean isFinish=false;

    public void shutDown(){
        isFinish = true;
    }

    public PrevProcessor(IRequestProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    @Override
    public void process(Request request) {
        //TODO:这里可以验证请求参数，验证完成之后交给下一个逻辑
        requests.add(request);
//        nextProcessor.process(request);
    }

    @Override
    public void run() {
        while(!isFinish){
            try {
                Request request = requests.take();
                //这边开始根据实际需求进行处理
            }catch (Exception e){

            }
        }
    }
}
