package com.learn.netty.reactor.singlethread;

import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/9/10
 * comment:分发事件处理任务的线程
 */
@Slf4j
public class DispatchThread implements Runnable {

    private Selector selector;

    public DispatchThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select(1000);
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    SelectionKey sk = it.next();
                    dispatch(sk);
                }
                selected.clear();
            }
        } catch (Exception ex) {
            log.error("事件分配失败，异常信息为:{}", ex);
        }
    }

    private void dispatch(SelectionKey selectionKey){
        IHandler handler = (IHandler) selectionKey.attachment();
        //调用之前attach绑定到选择键的handler处理器对象
        if (handler != null) {
            handler.handlerSelectKey();
        }
    }
}
