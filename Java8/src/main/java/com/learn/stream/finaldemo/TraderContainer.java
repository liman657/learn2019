package com.learn.stream.finaldemo;

import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/9/1
 * comment:
 */
public class TraderContainer {

    public static List<Transaction> getTransactionList(){
        Trader raoul = new Trader("Raoul","Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        return transactions;
    }
}