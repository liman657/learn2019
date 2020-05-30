package com.learn.collector.common;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:
 */
@Slf4j
public class TransactionContainer {

    public static List<Trader> getTraderList(){
        List<Trader> traderList = new ArrayList<>();
        //交易员
        Trader raoul  = new Trader("raoul","Cambridge");
        Trader mario  = new Trader("mario","Milan");
        Trader alan  = new Trader("alan","Cambridge");
        Trader brian  = new Trader("brian","Cambridge");

        traderList.add(raoul);
        traderList.add(mario);
        traderList.add(alan);
        traderList.add(brian);

        return traderList;
    }

    public static List<Transaction> getTransactionList(){
        //交易员
        Trader raoul  = new Trader("raoul","Cambridge");
        Trader mario  = new Trader("mario","Milan");
        Trader alan  = new Trader("alan","Cambridge");
        Trader brian  = new Trader("brian","Cambridge");
        //交易记录
        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian,2011,300),
                new Transaction(raoul,2012,1000),
                new Transaction(raoul,2011,400),
                new Transaction(mario,2012,710),
                new Transaction(mario,2012,700),
                new Transaction(alan,2012,950));
        return transactions;
    }
}
