package com.learn.springmvc.servlet;

import com.learn.springmvc.Annotation.SelfAutowired;
import com.learn.springmvc.Annotation.SelfController;
import com.learn.springmvc.Annotation.SelfService;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * autor:liman
 * comment: 自己的servlet
 */
public class SelfServlet extends HttpServlet {

    //用于保存application.properties配置文件中的内容，这个实例使用properties文件代替xml文件
    private Properties contextConfig = new Properties();

    //用于保存所有扫描出来的类名
    private List<String> classNames = new ArrayList<String>();

    //传说中的IOC容器，为了简化程序，这里不用ConcurrentHashMap
    private Map<String,Object> ioc = new HashMap<String,Object>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6.调用
        doDispatch(req,resp);
    }



    /**
     * 初始化方法
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        //2.扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        //3.初始化扫描的类
        doInstance();
        //4.完成依赖注入
        doAutowired();
        //5.初始化handleMapping
        initHandleMapping();

        System.out.println("自己写的spring mvc 初始化完成");

    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void initHandleMapping() {
    }

    /**
     * 完成依赖注入
     */
    private void doAutowired() {
        if(ioc.isEmpty()){
            return;
        }

        for(Map.Entry<String,Object> entry:ioc.entrySet()){
            //获取本类中所有的字段
            Field[] fields = entry.getValue().getClass().getDeclaredFields();

            for(Field field:fields){
                if(!field.isAnnotationPresent(SelfAutowired.class)){
                    continue;
                }
                //获取有@Autowired注解的属性上的注解对象
                SelfAutowired selfAutowired = field.getAnnotation(SelfAutowired.class);
                //获取注解的值
                String beanName = selfAutowired.value().trim();
                if("".equals(beanName)){
                    beanName = field.getType().getName();
                }

                //设置属性的访问属性
                field.setAccessible(true);

                try {
                    //设置属性的值
                    field.set(entry.getValue(),ioc.get(beanName));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 初始化，为DI做准备
     * 加了注解的类才能初始化，这里只列出加了@Controller和@Service注解的类
     */
    private void doInstance() {
        if(classNames.isEmpty()){return;}

        try{
            for(String className:classNames){
                Class<?> clazz = Class.forName(className);
                //如果有Controller注解
                if(clazz.isAnnotationPresent(SelfController.class)){
                    Object instance = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                }else if(clazz.isAnnotationPresent(SelfService.class)){//如果有Service注解
                    //获取自定义的beanName——@Service("test")获取其中的test
                    SelfService service = clazz.getAnnotation(SelfService.class);
                    String beanName = service.value();//获得注解的值（一般自己制定service的名称的时候）
                    if("".equals(beanName.trim())){
                        //如果没有指定名称，service 默认首字母小写
                        beanName = toLowerFirstCase(clazz.getSimpleName());
                    }


                    Object instance = clazz.newInstance();
                    //放入IOC容器
                    ioc.put(beanName,instance);

                    //注入的时候是接口注入的方式，投机取巧，就将接口作为key,实例作为值
                    for(Class<?> i:clazz.getInterfaces()){
                        if(ioc.containsKey(i.getName())){//这样就限制了，一个接口只有一个实现类
                            throw new Exception("the "+i.getName()+" is exists");
                        }
                        ioc.put(i.getName(),instance);
                    }
                }else{
                    continue;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 扫描出相关的类
     * @param scanPackage
     */
    private void doScanner(String scanPackage) {

        //主要是将包路径转换为文件路径
        URL url = this.getClass().getClassLoader().getResource("/"+scanPackage.replaceAll("\\.","/"));
        File classPath  = new File(url.getFile());
        for(File file:classPath.listFiles()){
            if(file.isDirectory()){
                doScanner(scanPackage+"."+file.getName());
            }else{
                if(file.getName().endsWith(".class")){continue;}
                String className = (scanPackage+"."+file.getName().replace(".class",""));
                classNames.add(className);
            }
        }

    }

    /**
     * 加载配置文件
     */
    private void doLoadConfig(String contextConfigLocation) {
        InputStream fis = null;
        fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null!=fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toLowerFirstCase(String simpleName) {
        char [] chars = simpleName.toCharArray();
        //之所以加，是因为大小写字母的ASCII码相差32，
        // 而且大写字母的ASCII码要小于小写字母的ASCII码
        //在Java中，对char做算学运算，实际上就是对ASCII码做算学运算
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
