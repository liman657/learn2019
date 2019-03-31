package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * autor:liman
 * comment:
 */
public class SelfClassLoader extends ClassLoader{

    private File classPathFile;

    public SelfClassLoader() {
        String classPath = SelfClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classPath);
    }

    /**
     * 查找指定的class文件，并将其解析成指定的对象
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String className = SelfClassLoader.class.getPackage().getName() + "." + name;

        if(classPathFile !=null){
            File classFile = new File(classPathFile,name.replaceAll("\\.","/")+".class");
            if(classFile.exists()){
                FileInputStream in = null;
                ByteArrayOutputStream out = null;
                try{
                    in = new FileInputStream(classFile);
                    out = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int len;
                    while((len =in.read(buff)) != -1 ){
                        out.write(buff,0,len);
                    }
                    return defineClass(className,out.toByteArray(),0,out.size());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
