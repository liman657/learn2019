package com.learn.thread.update.phase02.SingleGateThread;

/**
 * autor:liman
 * createtime:2020/7/6
 * comment:
 */
public class Passengers extends Thread {

    private final FlightSecurity flightSecurity;

    private final String idCard;

    private final String boardingPass;

    public Passengers(FlightSecurity flightSecurity,String idCard,String boardingPass){
        this.flightSecurity = flightSecurity;
        this.idCard = idCard;
        this.boardingPass = boardingPass;
    }

    @Override
    public void run() {
        while(true){
            flightSecurity.pass(boardingPass,idCard);
        }
    }
}
