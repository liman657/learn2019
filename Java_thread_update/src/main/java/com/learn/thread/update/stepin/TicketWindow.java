package com.learn.thread.update.stepin;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/2
 * comment:大厅里的机器
 */
@Slf4j
public class TicketWindow extends Thread {

    private final String name;
    private static final int MAX = 500;
    private int index = 1;

    public TicketWindow(String name){
        this.name = name;
    }

    @Override
    public void run() {
        while(index<=MAX){
            log.info("柜台名称:{},当前编号:{}",name,index++);
        }
    }

    public static void main(String[] args) {
        TicketWindow ticketWindow0 = new TicketWindow("一号叫号机");
        TicketWindow ticketWindow1 = new TicketWindow("二号叫号机");
        TicketWindow ticketWindow2 = new TicketWindow("三号叫号机");
        TicketWindow ticketWindow3 = new TicketWindow("四号叫号机");
        TicketWindow ticketWindow4 = new TicketWindow("五号叫号机");
        ticketWindow0.start();
        ticketWindow1.start();
        ticketWindow2.start();
        ticketWindow3.start();
        ticketWindow4.start();
    }
}
