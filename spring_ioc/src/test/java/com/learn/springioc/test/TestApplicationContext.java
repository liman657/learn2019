package com.learn.springioc.test;

import com.learn.springioc.demo.action.MyAction;
import com.learn.springioc.framework.context.SelfApplicationContext;
import org.junit.Test;

/**
 * autor:liman
 * createtime:2019/5/18
 * comment:
 */
public class TestApplicationContext {

    @Test
    public void testGetApplicationContext(){
        SelfApplicationContext applicationContext = new SelfApplicationContext("classpath:application.properties");
        System.out.println(applicationContext);
        MyAction myAction = (MyAction) applicationContext.getBean("myAction");
        System.out.println(myAction);
        System.out.println(myAction.getQueryService());
    }

}
