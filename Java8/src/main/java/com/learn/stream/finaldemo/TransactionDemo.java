package com.learn.stream.finaldemo;

import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * autor:liman
 * createtime:2019/9/1
 * comment: TransactionDemo，所有的实例
 */
public class TransactionDemo {

    public static void main(String[] args) {
        filterByYear(TraderContainer.getTransactionList(), 2011);

        workCitys(TraderContainer.getTransactionList());

        traderFromCambridge(TraderContainer.getTransactionList());

        orderTraderByName(TraderContainer.getTransactionList());

        hasTraderinMilan(TraderContainer.getTransactionList());

        sumTraderAmountFromCambridge(TraderContainer.getTransactionList());

        highestValue(TraderContainer.getTransactionList());
    }

    /**
     * 按照指定年份找出指定的交易，并按照交易金额排序
     *
     * @param transactionList
     * @param year
     */
    public static void filterByYear(List<Transaction> transactionList, int year) {
        List<Transaction> filterResult = transactionList.stream()
                .filter(t -> t.getYear() == year)
                .sorted(comparing(Transaction::getValue)).collect(toList());
        System.out.println(filterResult);
    }

    /**
     * 查找所有交易员工作过城市
     * @param transactionList
     */
    public static void workCitys(List<Transaction> transactionList) {
        List<String> cities = transactionList.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(cities);
    }

    public static void traderFromCambridge(List<Transaction> transactionList){
        List<Trader> cambridgeTraders = transactionList.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName)).collect(toList());
        System.out.println(cambridgeTraders);
    }

    /**
     * 将交易员按照名称排序
     * @param transactionList
     */
    public static void orderTraderByName(List<Transaction> transactionList){
        List<Trader> result = transactionList.stream()
                .map(transaction -> transaction.getTrader())
                .sorted(comparing(trader -> trader.getName()))
                .collect(toList());
        System.out.println(result);
    }

    /**
     * 查询是否有来自米兰的交易员
     * @param transactionList
     */
    public static void hasTraderinMilan(List<Transaction> transactionList){
        boolean hasMilan = transactionList.stream()
                .map(transaction -> transaction.getTrader())
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        System.out.println(hasMilan);
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额
     * @param transactionList
     */
    public static void sumTraderAmountFromCambridge(List<Transaction> transactionList){
        List<Integer> cambridgeTrader = transactionList.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(toList());
        System.out.println(cambridgeTrader);
    }

    /**
     * 所有交易中最高的交易额
     * @param transactionList
     */
    public static void highestValue(List<Transaction> transactionList){
        Optional<Integer> highestTraderValue = transactionList.stream()
                .map(transaction -> transaction.getValue())
                .reduce(Integer::max);
        System.out.println(highestTraderValue.get());
    }
}