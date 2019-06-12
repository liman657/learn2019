package com.learn.transaction.service.impl;

import com.learn.transaction.dao.AccountDao;
import com.learn.transaction.service.SMAccountService;
import org.springframework.transaction.annotation.Transactional;

/**
 * author:liman
 * createtime:2019/6/12
 */
@Transactional
public class SMAccountServiceImpl implements SMAccountService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String out, String in, Double money) {
        System.out.println("============开始转账=============");
        accountDao.outMoney(out,money);
        int i = 1/0;
        accountDao.inMoney(in,money);
        System.out.println("============转账完成=============");
    }
}
