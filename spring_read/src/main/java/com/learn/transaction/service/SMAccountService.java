package com.learn.transaction.service;

/**
 * author:liman
 * createtime:2019/6/12
 * comment:声明式事务对应的转账接口
 */
public interface SMAccountService {

    public void transfer(String out, String in, Double money);

}
