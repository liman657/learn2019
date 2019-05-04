package com.learn.springioc.framework.context;

import com.learn.springioc.framework.beans.SelfBeanFactory;
import com.learn.springioc.framework.beans.config.SelfBeanDefinition;
import com.learn.springioc.framework.beans.support.SelfBeanDefinitionReader;
import com.learn.springioc.framework.beans.support.SelfDefaultListableBeanFactory;

import java.util.List;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment: 真正的spring 中ApplicationContext只是一个接口，有多个实现类
 * 有基于注解的工厂，有基于XML配置文件的工厂，同时还有一个refresh方法，这个是在一个默认的SelfBeanFactory实现类中
 *
 */
public class SelfApplicationContext extends SelfDefaultListableBeanFactory implements SelfBeanFactory {

    private String[] configLocations;

    private SelfBeanDefinitionReader selfBeanDefinitionReader;

    public SelfApplicationContext(String ... configLocations) {
        this.configLocations = configLocations;
        try{
            refresh();
        }catch (Exception e){

        }
    }

    @Override
    protected void refresh() throws Exception {

        //1.定位配置文件
        selfBeanDefinitionReader = new SelfBeanDefinitionReader(this.configLocations);

        //2.加载配置文件，扫描相关的类，把他们封装成BeanDefinition
        List<SelfBeanDefinition> beanDefinitions = selfBeanDefinitionReader.loadBeanDefinitions();

        //3.将扫描到的bean注册到容器中
        doRegisterBeanDefinition(beanDefinitions);

        //4.将非懒加载的实例进行初始化

    }

    private void doRegisterBeanDefinition(List<SelfBeanDefinition> beanDefinitions) throws Exception {
        for(SelfBeanDefinition beanDefinition:beanDefinitions){
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The \""+beanDefinition.getFactoryBeanName()+"\" is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
        //至此，容器初始化完成
    }


    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
