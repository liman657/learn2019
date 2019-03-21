package com.learn.designModel.SingletonPattern.SerializableProblem;

import com.learn.designModel.SingletonPattern.Hungary.HungarySingleton;

import java.io.*;

/**
 * autor:liman
 * comment:序列化破坏单例的情况
 */
public class SerializableProblem {

    public static void main(String[] args) {
        HungarySingleton hungarySingleton01 = null;
        HungarySingleton hungarySingleton02 = HungarySingleton.getInstance();

        FileOutputStream fileOutputStream = null;
        try{
            //将获得的单例序列化到一个文件中
            fileOutputStream = new FileOutputStream("SerializableObj.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(hungarySingleton02);
            oos.flush();
            oos.close();

            //从文件中读取序列化的对象（反序列化）
            FileInputStream fileInputStream = new FileInputStream("SerializableObj.obj");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hungarySingleton01 = (HungarySingleton) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println(hungarySingleton01);
            System.out.println(hungarySingleton02);
            System.out.println(hungarySingleton01==hungarySingleton02);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
