package com.learn.designModel.ProxyPattern.dynamicProxy.JDKProxy;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * autor:liman
 * comment:
 */
public class JDKProxyTest {

    public static void main(String[] args) throws IOException {
        Person person = (Person) new JDKMeipo().getInstance(new Girl());
        person.seeBoy();

        //输出代理类的源码，方便后面分析
        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
        FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
        os.write(bytes);
        os.close();

        Person boy = (Person) new JDKMeipo().getInstance(new Boy());
        boy.seeBoy();
    }

}
