package com.learn.transaction.service;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
public interface AccountService {

    public void transfer(String out, String in, Double money);

    public void transferInTransaction(String out, String in, Double money);

}
