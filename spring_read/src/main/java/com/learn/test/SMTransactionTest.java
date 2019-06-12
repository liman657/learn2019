package com.learn.test;

import com.learn.transaction.service.AccountService;
import com.learn.transaction.service.SMAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * autor:liman
 * createtime:2019/6/11
 * comment:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:tx/applicationContext-AnnoTx.xml")
public class SMTransactionTest {

    @Resource(name="accountServiceProxy")
    private SMAccountService accountService;

    @Test
    public void testTranout(){
        accountService.transfer("aaa","bbb",200D);
    }

//    @Test
//    public void testTranoutInTransaction(){
//        accountService.transferInTransaction("aaa","bbb",200D);
//    }

}
