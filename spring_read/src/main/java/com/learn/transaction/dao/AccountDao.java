package com.learn.transaction.dao;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
public interface AccountDao {

    public void outMoney(String out, Double money);

    public void inMoney(String in, Double money);

}
