package com.learn.transaction.service.impl;

import com.learn.transaction.dao.AccountDao;
import com.learn.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
public class AccountServiceImpl implements AccountService {

//    @Autowired
    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String out, String in, Double money) {
        accountDao.outMoney(out,money);
        accountDao.inMoney(in,money);
    }
}
