package com.learn.designModel.SingletonPattern.registerSingleton;

import com.learn.designModel.SingletonPattern.Hungary.HungarySingleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * autor:liman
 * comment:
 */
public class EnumSingletonTest {

    public static void main(String[] args) {
        EnumSingleton02 hungarySingleton01 = null;
        EnumSingleton02 hungarySingleton02 = EnumSingleton02.getInstance();
        hungarySingleton02.setData(new Object());

        FileOutputStream fileOutputStream = null;
        try{
            //将获得的单例序列化到一个文件中
            fileOutputStream = new FileOutputStream("EnumSingleton.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(hungarySingleton02);
            oos.flush();
            oos.close();

            //从文件中读取序列化的对象（反序列化）
            FileInputStream fileInputStream = new FileInputStream("EnumSingleton.obj");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            hungarySingleton01 = (EnumSingleton02) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println(hungarySingleton01.INSTANCE.getData());
            System.out.println(hungarySingleton02.INSTANCE.getData());
            System.out.println(hungarySingleton01.INSTANCE.getData()==hungarySingleton02.INSTANCE.getData());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
