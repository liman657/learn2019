package com.learn.thread.update.phase01.stepin;

/**
 * autor:liman
 * createtime:2020/6/4
 * comment:
 */
public class TicketWindowUpdate {

    public static void main(String[] args) {
        TicketWindowRunnable ticketWindow0 = new TicketWindowRunnable("一号叫号机");
        TicketWindowRunnable ticketWindow1 = new TicketWindowRunnable("二号叫号机");
        TicketWindowRunnable ticketWindow2 = new TicketWindowRunnable("三号叫号机");
        TicketWindowRunnable ticketWindow3 = new TicketWindowRunnable("四号叫号机");
        TicketWindowRunnable ticketWindow4 = new TicketWindowRunnable("五号叫号机");
        Thread ticketThread01 = new Thread(ticketWindow0);
        Thread ticketThread02 = new Thread(ticketWindow1);
        Thread ticketThread03 = new Thread(ticketWindow2);
        Thread ticketThread04 = new Thread(ticketWindow3);
        Thread ticketThread05 = new Thread(ticketWindow4);
        ticketThread01.start();
        ticketThread02.start();
        ticketThread03.start();
        ticketThread04.start();
        ticketThread05.start();
    }
}
