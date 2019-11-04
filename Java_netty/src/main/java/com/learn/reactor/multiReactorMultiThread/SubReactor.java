package com.learn.reactor.multiReactorMultiThread;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2019/11/3
 * comment:
 */
@Slf4j
public class SubReactor implements Runnable {

    private final Selector selector;
    private boolean register = false; //注册开关表示，为什么要加这么个东西，可以参考Acceptor设置这个值那里的描述
    private int num; //序号，也就是Acceptor初始化SubReactor时的下标

    SubReactor(Selector selector, int num) {
        this.selector = selector;
        this.num = num;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    @Override
    public void run() {
        while(!Thread.interrupted()){
            log.info("{}号subReactor等待注册中......",num);
            while(!Thread.interrupted()&&!register){
                try {
                    if(selector.select()==0){
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Set<SelectionKey> selectKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectKeys.iterator();
                while(iterator.hasNext()){
                    dispatch(iterator.next());
                    iterator.remove();
                }
            }
        }
    }

    private void dispatch(SelectionKey key) {
        Runnable r = (Runnable) (key.attachment());
        if (r != null) {
            r.run();
        }
    }
}
