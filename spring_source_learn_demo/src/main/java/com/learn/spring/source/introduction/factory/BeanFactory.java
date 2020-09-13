package com.learn.spring.source.introduction.factory;

import com.learn.spring.source.introduction.dao.DemoDao;
import com.learn.spring.source.introduction.dao.impl.DemoDaoImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * autor:liman
 * createtime:2020/9/2
 * comment:
 *
 * 静态工厂可将多处依赖抽取分离
 * 外部化配置文件+反射可解决配置的硬编码问题
 * 缓存可控制对象实例数
 *
 *  private DemoDao dao = new DemoDaoImpl();
 *  private DemoDao dao = (DemoDao) BeanFactory.getBean("demoDao");
 *  本来咱开发者可以使用上面的方式，主动声明实现类，但如果选择下面的方式，
 *  那就不再是咱自己去声明，而是将获取对象的方式交给了 BeanFactory 。
 *  这种将控制权交给别人的思想，就可以称作：控制反转（ Inverse of Control , IOC ）。
 * 而 BeanFactory 根据指定的 beanName 去获取和创建对象的过程，就可以称作：依赖查找（ Dependency Lookup , DL ）。
 */
@Slf4j
public class BeanFactory {

    private static Properties properties;

    // 缓存区，保存已经创建好的对象，为了保证获取的对象是单例的
    private static Map<String, Object> beanMap = new HashMap<>();

    static {
        properties = new Properties();
        try{
            // 必须使用类加载器读取resource文件夹下的配置文件
            properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("factory.properties"));
        }catch (Exception e){
            // BeanFactory类的静态初始化都失败了，那后续也没有必要继续执行了
            throw new ExceptionInInitializerError("BeanFactory initialize error, cause: " + e.getMessage());
        }
    }

    public static DemoDao getDemoDao(){
        return new DemoDaoImpl();
    }

    /**
     * 这里可以通过配置获取指定的bean，但是是多例的
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        try {
            // 从properties文件中读取指定name对应类的全限定名，并反射实例化
            Class<?> beanClazz = Class.forName(properties.getProperty(beanName));
            return beanClazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("BeanFactory have not [" + beanName + "] bean!", e);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("[" + beanName + "] instantiation error!", e);
        }
    }

    public static Object getBeanSingleton(String beanName) {
        // 双检锁保证beanMap中确实没有beanName对应的对象
        if (!beanMap.containsKey(beanName)) {
            synchronized (BeanFactory.class) {
                if (!beanMap.containsKey(beanName)) {
                    // 过了双检锁，证明确实没有，可以执行反射创建
                    try {
                        Class<?> beanClazz = Class.forName(properties.getProperty(beanName));
                        Object bean = beanClazz.newInstance();
                        // 反射创建后放入缓存再返回
                        beanMap.put(beanName, bean);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("BeanFactory have not [" + beanName + "] bean!", e);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException("[" + beanName + "] instantiation error!", e);
                    }
                }
            }
        }
        return beanMap.get(beanName);
    }


}
