package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * autor:liman
 * comment:
 */
public class SelfProxy {


    public static final String ln = "\r\n";

    public static Object newProxyInstance(SelfClassLoader loader,
                                          Class<?>[] interfaces,
                                          SelfInvocationHandler h){
        try{
            //1、生成$Proxy0的源代码
            String src = generateSource(interfaces);
            //2、将源代码写入到磁盘
            String filePath = SelfProxy.class.getResource("").getPath();
            //如果类文件中有多proxy的时候，后面的数字会自增
            File file = new File(filePath+"$Proxy0.java");
            FileWriter fw = new FileWriter(file);
            fw.write(src);
            fw.flush();
            fw.close();

            //3、将代码加载到虚拟机
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(file);

            //创建一个编译任务
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            //加载代理对象
            Class proxyClass = loader.findClass("$Proxy0");

            //返回构造的代理对象
            Constructor c = proxyClass.getConstructor(SelfInvocationHandler.class);
            return c.newInstance(h);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成代理的源代码
     * @param interfaces
     * @return
     */
    private static String generateSource(Class<?>[] interfaces) {

        StringBuffer sb = new StringBuffer();
        sb.append("package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;"+ln);
        sb.append("import com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy.Person;;"+ln);
        sb.append("import java.lang.reflect.*;"+ln);
        sb.append("public class $Proxy0 implements "+interfaces[0].getName()+"{"+ln);

            sb.append("SelfInvocationHandler h;"+ln);
            sb.append("public $Proxy0(SelfInvocationHandler h){"+ln);
                sb.append("this.h=h;"+ln);
            sb.append("}"+ln);

            for(Method m:interfaces[0].getMethods()){
                sb.append("public final "+m.getReturnType().getName() + " "+m.getName()+"(){"+ln);
                    sb.append("try {"+ln);
                        sb.append("Method m = "+interfaces[0].getName()+".class.getMethod(\""+m.getName()+"\",new Class[]{});"+ln);
                        sb.append("this.h.invoke(this,m,null);"+ln);
                    sb.append("} catch(Throwable e){"+ln);
                        sb.append("e.printStackTrace();"+ln);
                    sb.append("}"+ln);
                sb.append("}"+ln);
            }
        sb.append("}"+ln);


        return sb.toString();
    }
}
