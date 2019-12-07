package com.learn.jvm.chapter09;

/**
 * autor:liman
 * createtime:2019/12/1
 * comment: class文件的实例代码
 */
public class SimpleUser {

    public static final int TYPE = 1;

    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        try{
            this.id = id;
        }catch (IllegalStateException e){
            System.out.println(e);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
