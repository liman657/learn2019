package com.learn.spring.source.quickstartdi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * autor:liman
 * createtime:2020/9/4
 * comment:
 */
@Slf4j
public class QuickStartByTypeApplication {

    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("base_di/quickstart-bytype.xml");
        Person person = factory.getBean(Person.class);
        log.info("从spring中获取的person:{}",person.toString());
    }

}
