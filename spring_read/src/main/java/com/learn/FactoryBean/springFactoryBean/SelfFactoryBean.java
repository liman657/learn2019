package com.learn.FactoryBean.springFactoryBean;

import com.learn.FactoryBean.factory.Stage;
import org.springframework.beans.factory.FactoryBean;

/**
 * autor:liman
 * createtime:2019/6/9
 * comment:
 */
public class SelfFactoryBean implements FactoryBean {


    @Override
    public Object getObject() throws Exception {
        Stage stage = new Stage();
        stage.setStageName("For huifang");
        return stage;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
