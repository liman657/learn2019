package com.learn.collector.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:
 */
@Slf4j
@Data
public class Transaction {

    private final Trader trader;
    private final int year;
    private final int value;

    @Override
    public String toString() {
        return "Transaction{" +
                "trader=" + trader +
                ", year=" + year +
                ", value=" + value +
                '}';
    }
}
