package com.learn.designModel.SingletonPattern.ContainerSingleton;

/**
 * autor:liman
 * comment: 容器式单例测试
 */
public class ContainerSingletonTest {

    public static void main(String[] args) {
        Object bean = ContainerSingleton.getBean("com.learn.designModel.SingletonPattern.ContainerSingleton.DTOTest");



        System.out.println(bean);
    }

}
