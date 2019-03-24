package com.learn.designModel.PrototypePattern.deepClone;

import sun.security.provider.Sun;

import java.io.*;
import java.util.Date;

/**
 * autor:liman
 * comment:
 */
public class SunWuKong extends Monkey implements Serializable,Cloneable {

    private GoldenCudgel goldenCudgel;

    public SunWuKong(){
        this.birthday = new Date();
        this.goldenCudgel = new GoldenCudgel();
    }

    public GoldenCudgel getGoldenCudgel() {
        return goldenCudgel;
    }

    public void setGoldenCudgel(GoldenCudgel goldenCudgel) {
        this.goldenCudgel = goldenCudgel;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public Object deepClone(){
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);

            SunWuKong sunWuKong = (SunWuKong) ois.readObject();
            sunWuKong.birthday = new Date();

            bis.close();
            ois.close();
            oos.close();
            bos.close();
            return sunWuKong;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}