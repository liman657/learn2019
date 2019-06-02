package com.learn.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * autor:liman
 * createtime:2019/6/1
 * comment:
 */
public class FactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
            System.out.println("******调用了BeanFactoryPostProcessor");
            String[] beanStr = configurableListableBeanFactory
                    .getBeanDefinitionNames();
            for (String beanName : beanStr) {
                if ("beanFactoryPostProcessorTest".equals(beanName)) {
                    BeanDefinition beanDefinition = configurableListableBeanFactory
                            .getBeanDefinition(beanName);
                    MutablePropertyValues m = beanDefinition.getPropertyValues();
                    if (m.contains("name")) {
                        m.addPropertyValue("name", "赵四");
                        System.out.println("》》》修改了name属性初始值了");
                    }
                }
            }
    }
}
