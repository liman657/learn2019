package com.learn.collector.selfcollector;

import com.learn.collector.common.Trader;
import com.learn.collector.common.Transaction;
import com.learn.collector.common.TransactionContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:没有collector的时代
 */
@Slf4j
public class OutOfCollector {

    public static void main(String[] args) {
        List<Transaction> transactionList = TransactionContainer.getTransactionList();
        outOfUseCollector(transactionList);
        useCollector(transactionList);
    }

    /**
     * 没有使用collector
     * @param transactionList
     */
    public static void outOfUseCollector(List<Transaction> transactionList){
        Map<Integer,List<Transaction>> transactionByYear = new HashMap<>();
        for(Transaction transaction:transactionList){
            int year = transaction.getYear();
            List<Transaction> transactionYear = transactionByYear.get(year);
            if(transactionYear == null){
                transactionYear = new ArrayList<>();
                transactionByYear.put(year,transactionYear);
            }
            transactionYear.add(transaction);
        }
        log.info("{}",transactionByYear);
    }

    /**
     * 使用collector
     * @param transactionList
     */
    public static void useCollector(List<Transaction> transactionList){
        Map<Integer, List<Transaction>> result = transactionList.stream().collect(groupingBy(Transaction::getYear));
        log.info("use collector result : {}",result);
    }

}
