package com.learn.thread.update.phase02.SingleGateThread;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/7/6
 * comment:机场安检实例
 */
@Slf4j
public class FlightSecurity {

    private int count = 0;
    private String boardingPass = "null";
    private String idCard = "null";

    public synchronized void pass(String boardingPass, String idCard) {
        this.boardingPass = boardingPass;
        this.idCard = idCard;
        this.count++;
        check();
    }

    private void check() {
        if(boardingPass.charAt(0)!=idCard.charAt(0)){
            throw new RuntimeException("Not Pass Security!"+toString());
        }
    }

    @Override
    public String toString() {
        return "The "+count+" passengers,boardinPass ["+boardingPass +" ],idCard [ "+idCard+" ]";
    }
}
