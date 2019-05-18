package com.learn.springioc.framework.beans.support;

import com.learn.springioc.framework.beans.config.SelfBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * autor:liman
 * createtime:2019/5/4
 * comment: 用于读取配置文件中的信息
 */
public class SelfBeanDefinitionReader {

    private List<String> registryBeanClasses = new ArrayList<String>();

    private Properties config = new Properties();

    private final String SCAN_PACKAGE="scanPackage";

    //构造函数，同时用于读取配置文件
    public SelfBeanDefinitionReader(String... locations) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    /**
     * 扫描指定的报名，获取包下面的类名称，放入指定集合中。
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {
        //转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registryBeanClasses.add(className);
            }
        }
    }

    public Properties getConfig(){
        return this.config;
    }

    /**
     * 把每一个定义信息解析成beanDefinition
     * @return
     */
    public List<SelfBeanDefinition> loadBeanDefinitions(){
        List<SelfBeanDefinition> result = new ArrayList<SelfBeanDefinition>();
        try{
            for(String className:registryBeanClasses){
                Class<?> beanClass = Class.forName(className);
                if(beanClass.isInterface()){
                    continue;
                }

                /**
                 * 一般beanName有三种情况
                 * 1、默认情况是首字母消息
                 * 2、自定义名字
                 * 3、接口注入
                 */
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));

                Class<?>[] interfaces = beanClass.getInterfaces();
                for(Class<?> i:interfaces){
                    //如果是多个实现类，只能覆盖

                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将每个配置信息解析成一个BeanDefinition
     * @param factoryBeanName
     * @param beanClassName
     * @return
     */
    private SelfBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        SelfBeanDefinition selfBeanDefinition = new SelfBeanDefinition();
        selfBeanDefinition.setBeanClassName(beanClassName);
        selfBeanDefinition.setFactoryBeanName(factoryBeanName);
        return selfBeanDefinition;
    }


    //如果类名本身是小写字母，确实会出问题
    //但是我要说明的是：这个方法是我自己用，private的
    //传值也是自己传，类也都遵循了驼峰命名法
    //默认传入的值，存在首字母小写的情况，也不可能出现非字母的情况

    //为了简化程序逻辑，就不做其他判断了，大家了解就OK
    //其实用写注释的时间都能够把逻辑写完了
    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
