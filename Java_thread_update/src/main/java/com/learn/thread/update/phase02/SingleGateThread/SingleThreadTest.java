package com.learn.thread.update.phase02.SingleGateThread;

/**
 * autor:liman
 * createtime:2020/7/6
 * comment:
 */
public class SingleThreadTest {

    public static void main(String[] args) {
        final FlightSecurity flightSecurity = new FlightSecurity();
        new Passengers(flightSecurity,"A12345","AF123456").start();
        new Passengers(flightSecurity,"B12345","BF123456").start();
        new Passengers(flightSecurity,"C12345","CF123456").start();
    }

}
