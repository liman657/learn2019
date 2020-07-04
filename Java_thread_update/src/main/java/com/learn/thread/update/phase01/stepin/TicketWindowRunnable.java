package com.learn.thread.update.phase01.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/2
 * comment:大厅里的机器
 */
@Slf4j
public class TicketWindowRunnable implements Runnable {

    private final String name;
    private static final int MAX = 500;
    private int index = 1;

    public TicketWindowRunnable(String name){
        this.name = name;
    }

    public void run() {
        while(index<=MAX){
            log.info("柜台名称:{},当前编号:{}",name,index++);
        }
    }
}
