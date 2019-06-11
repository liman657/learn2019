package com.learn.test;

//import com.learn.FactoryBean.Stage;
import com.learn.FactoryBean.factory.Stage;
import com.learn.FactoryBean.factory.StageFactory;
import com.learn.FactoryBean.factoryBean.ABean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * autor:liman
 * createtime:2019/6/5
 * comment:
 */
public class FactoryBeanTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext-factoryBean.xml");
//        Dancer dancer = (Dancer) applicationContext.getBean("dancer");
//        dancer.Show();
//
//        Stage stage = (Stage) applicationContext.getBean("stage");
//        Stage instance = StageFactory.getInstance();
//        stage.Show();
//        System.out.println(instance == stage);

//        ABean aBean = (ABean) applicationContext.getBean("abean");
//        aBean.show();
//
//        Stage stage = (Stage) applicationContext.getBean("stage");
//        stage.CreateStageSuccess();

        Stage stageForHuifang = (Stage) applicationContext.getBean("stageForHuifang");
        stageForHuifang.Show();
    }

}
