package com.learn.transaction.dao.impl;

import com.learn.transaction.dao.AccountDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
//@Component("accountDao")
public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {
    @Override
    public void outMoney(String out, Double money) {
        String sql = "update account set money = money-? where name = ?";
        this.getJdbcTemplate().update(sql,money,out);
    }

    @Override
    public void inMoney(String in, Double money) {
        String sql = "update account set money = money+? where name = ?";
        this.getJdbcTemplate().update(sql,money,in);
    }
}
