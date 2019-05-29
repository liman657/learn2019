package com.learn.springioc.framework.context;

import com.learn.springioc.framework.annotation.SelfAutowired;
import com.learn.springioc.framework.annotation.SelfController;
import com.learn.springioc.framework.annotation.SelfService;
import com.learn.springioc.framework.beans.SelfBeanFactory;
import com.learn.springioc.framework.beans.SelfBeanWrapper;
import com.learn.springioc.framework.beans.config.SelfBeanDefinition;
import com.learn.springioc.framework.beans.support.SelfBeanDefinitionReader;
import com.learn.springioc.framework.beans.support.SelfDefaultListableBeanFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment: 真正的spring 中ApplicationContext只是一个接口，有多个实现类
 * 有基于注解的工厂，有基于XML配置文件的工厂，同时还有一个refresh方法，这个是在一个默认的SelfBeanFactory实现类——DefaultListableBeanFactory中
 */
public class SelfApplicationContext extends SelfDefaultListableBeanFactory implements SelfBeanFactory {

    private String[] configLocations;

    private SelfBeanDefinitionReader selfBeanDefinitionReader;

    //缓存单例的ioc容器
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private Map<String, SelfBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(256);

    public SelfApplicationContext(String... configLocations) {
        this.configLocations = configLocations;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
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

        //4.将非懒加载的实例进行初始化(注入)
        doAutowired();

    }

    /**
     * 动态注入，只处理非延时加载的情况
     * 在getBean的时候完成动态注入
     * <p>
     * 至此，spring ioc容器初始化完成
     */
    private void doAutowired() {
        for (Map.Entry<String, SelfBeanDefinition> entry : super.beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            if (!entry.getValue().isLazyInit()) {//如果不是懒加载
                getBean(beanName);
            }
        }

    }

    private void doRegisterBeanDefinition(List<SelfBeanDefinition> beanDefinitions) throws Exception {
        for (SelfBeanDefinition beanDefinition : beanDefinitions) {
            if (super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())) {
                throw new Exception("The \"" + beanDefinition.getFactoryBeanName() + "\" is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
        }
        //至此，容器初始化完成
    }


    @Override
    public Object getBean(String beanName) {

        /**
         * 将初始化分为两步走，目的就是为了解决循环依赖的问题。
         */
        //1.doCreateBean
        //1.1 instantsBean //初始化bean //spring中这个方法是在AbstractAutowireCapableBeanFactory
        SelfBeanDefinition selfBeanDefinition = this.beanDefinitionMap.get(beanName);
        SelfBeanWrapper selfBeanWrapper = instantiateBean(beanName, selfBeanDefinition);

        //拿到BeanWrapper之后，将BeanWrapper保存到IOC容器中去
        this.factoryBeanInstanceCache.put(beanName, selfBeanWrapper);
        //1.2 populateBean //注入bean
        populateBean(beanName, new SelfBeanDefinition(), selfBeanWrapper);//将BeanDefinition变成BeanWrapper
        return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
    }

    /**
     * 真正的IOC容器，是保存String,BeanWrapper键值对，所以instantiateBean要返回BeanWrapper
     *
     * @param beanName
     * @param selfBeanDefinition
     * @param selfBeanWrapper
     */
    private void populateBean(String beanName, SelfBeanDefinition selfBeanDefinition, SelfBeanWrapper selfBeanWrapper) {

        Object instance = selfBeanWrapper.getWrappedInstance();
        //1.判断是否有注解
        Class<?> clazz = selfBeanWrapper.getWrappedClass();
        if (!(clazz.isAnnotationPresent(SelfController.class) || clazz.isAnnotationPresent(SelfService.class))) {
            return;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(SelfAutowired.class)) {
                continue;
            }
            SelfAutowired autowired = field.getAnnotation(SelfAutowired.class);
            String autowiredBeanName = autowired.value().trim();
            if ("".equals(autowiredBeanName)) {
                autowiredBeanName = field.getType().getName();
            }

            //强制访问，给属性赋值，至此DI完成
            field.setAccessible(true);
            try {
                //TODO:会有空指针异常
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){
                    continue;
                }
                field.set(instance,this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private SelfBeanWrapper instantiateBean(String beanName, SelfBeanDefinition selfBeanDefinition) {

        //1.拿到要实例化的类名，
        String className = selfBeanDefinition.getBeanClassName();
        //2.进行反射实例化，得到一个对象
        Object instance = null;
        try {
            if (this.singletonObjects.containsKey(className)) {
                instance = this.singletonObjects.get(className);
            } else {
                Class<?> clazz = Class.forName(className);
                instance = clazz.newInstance();
                this.singletonObjects.put(className, instance);
                this.singletonObjects.put(selfBeanDefinition.getFactoryBeanName(), instance);
            }

        } catch (Exception e) {

        }
        //3.把这个对象封装到BeanWrapper中 //sigletonObjects 是一个缓存保存了所有单例的对象。//factoryBeanInstanceCache，IOC容器缓存
        SelfBeanWrapper beanWrapper = new SelfBeanWrapper(instance);
        //4.把BeanWrapper存放到IOC容器中（真正的IOC容器）
        return beanWrapper;
    }

    public String[] getBeanDefinitionNames(){
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount(){
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig(){
        return this.selfBeanDefinitionReader.getConfig();
    }
}
