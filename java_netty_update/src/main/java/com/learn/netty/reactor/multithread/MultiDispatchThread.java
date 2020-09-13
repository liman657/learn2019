package com.learn.netty.reactor.multithread;

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
public class MultiDispatchThread implements Runnable {

    private Selector selector;

    public MultiDispatchThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                selector.select(1000);
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    //Reactor负责dispatch收到的事件
                    SelectionKey sk = it.next();
                    log.info("测试一下，分配{}中的事件，事件类型为{}，事件数为:{}", selector, sk.readyOps(), selected.size());
                    dispatch(sk);
                }
                selected.clear();
            } catch (Exception ex) {
                //出现异常继续分配下一个事件，留个日志即可
                log.error("事件分配失败，异常信息为:{}", ex.getStackTrace());
            }
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        IMultiHandler handler = (IMultiHandler) selectionKey.attachment();
        //调用之前attach绑定到选择键的handler处理器对象
        if (handler != null) {
            handler.handlerSelectKey();
        }
    }
}
