package com.learn.transaction.service.impl;

import com.learn.transaction.dao.AccountDao;
import com.learn.transaction.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
public class AccountServiceImpl implements AccountService {

//    @Autowired
    private AccountDao accountDao;

    private TransactionTemplate transactionTemplate;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public void transfer(String out, String in, Double money) {
        System.out.println("============开始转账=============");
        accountDao.outMoney(out,money);
        System.out.println("============出账成功=============");
        accountDao.inMoney(in,money);
        System.out.println("============转账完成=============");
    }

    public void transferInTransaction(String out,String in,Double money){
        //这里需要手动的去更改业务代码，十分不方便，因此称为编程式事务管理
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                accountDao.outMoney(out,money);
//                int i = 1/0;
                accountDao.inMoney(in,money);
            }
        });
    }
}
