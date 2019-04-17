package com.learn.foundation.reflect.ClassInfo;

/**
 * autor:liman
 * comment: 一个实体，用户说明Class包含了那些信息
 */
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void test(String name,Integer age){
        System.out.println("调用test方法成功，参数是："+name+","+age);
    }

    private String methodInSuper(int age){
        System.out.println("父类中的私有方法，参数：age,"+age);
        return String.valueOf(age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
