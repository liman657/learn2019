package com.learn.springioc.framework.webmvc.servlet;

import com.learn.springioc.framework.annotation.SelfController;
import com.learn.springioc.framework.annotation.SelfRequestMapping;
import com.learn.springioc.framework.context.SelfApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * autor:liman
 * createtime:2019/5/23
 * comment: dispatchServlet的入口
 */
public class SelfDispatchServlet extends HttpServlet {

    private SelfApplicationContext selfApplicationContext;

    private final String CONTEXT_CONFIG_LOCATION = "contextConfigLocation";

    private List<SelfHandlerMapping> handlerMappings = new ArrayList<SelfHandlerMapping>();

    private Map<SelfHandlerMapping,SelfHandlerAdapter> handlerAdapters = new HashMap<SelfHandlerMapping,SelfHandlerAdapter>();

    private List<SelfViewResolver> viewResolvers=new ArrayList<>();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.doDispatch(req,resp);
        } catch (Exception e) {
//            SelfModelAndView modelAndView = new SelfModelAndView("500");
//            processDispatchResult(req,resp,modelAndView);
            e.printStackTrace();
        }
    }

    /**
     * 真正的mvc入口
     * @param req
     * @param resp
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        //1.通过从request中获取url，去匹配一个HandlerMapping
        SelfHandlerMapping handler = getHandler(req);
        if(handler == null){
            SelfModelAndView modelAndView = new SelfModelAndView("404");
            processDispatchResult(req,resp,modelAndView);
            return ;
        }

        //2.获取handlerAdapter
        SelfHandlerAdapter handlerAdapter = getHandlerAdapter(handler);

        //3.调用方法，返回ModelAndView
        SelfModelAndView modelAndView = handlerAdapter.handle(req,resp,handler);

        //4.处理返回结果，将ModelAndView处理成对应的HTML，这一步才会真正去处理ModelAndView
        processDispatchResult(req,resp,modelAndView);
    }

    /**
     * 将ModelAndView转换成对应的HTML或者JSON
     * @param req
     * @param resp
     * @param modelAndView
     */
    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, SelfModelAndView modelAndView) throws Exception {
        if(null == modelAndView){//不用处理结果
            return;
        }

        if(this.viewResolvers.isEmpty()){//没有视图解析器
            return;
        }

        for(SelfViewResolver viewResolver:this.viewResolvers){
            SelfView selfView = viewResolver.resolveViewName(modelAndView.getViewName(),null);//获取视图
            selfView.render(modelAndView.getModel(),req,resp);//渲染视图
        }
    }

    /**
     * 根据handlerMapper获取handlerAdapter
     * @param handler
     * @return
     */
    private SelfHandlerAdapter getHandlerAdapter(SelfHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){
            return null;
        }
        SelfHandlerAdapter handlerAdapter = this.handlerAdapters.get(handler);
        if(handlerAdapter.support(handler)){
            return handlerAdapter;
        }
        return null;
    }

    /**
     * 根据url获取对应的HandlerMapping，这里就将HandlerMapping当做Handler来处理
     * @param req
     * @return
     */
    private SelfHandlerMapping getHandler(HttpServletRequest req){
        if(this.handlerMappings.isEmpty()){
            return null;
        }
        //获取url
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath,"").replaceAll("/+","/");

        for(SelfHandlerMapping handler:this.handlerMappings){
            try{
                Matcher matcher = handler.getPattern().matcher(url);
                if(!matcher.matches()){
                    continue;
                }
                return handler;
            }catch (Exception e){
                throw e;
            }
        }
        return null;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        //1.初始化ApplicationContext
        SelfApplicationContext selfApplicationContext = new SelfApplicationContext(config.getInitParameter(CONTEXT_CONFIG_LOCATION));
        //2.初始化SpringMvc的九大组件
        initStrategies(selfApplicationContext);
    }

    /**
     * 初始化九大组件
     * @param context
     */
    protected void initStrategies(SelfApplicationContext context) {
        //多文件上传组件
        initMultipartResolver(context);
        //本地化语言环境组件
        initLocaleResolver(context);
        //模板处理器组件
        initThemeResolver(context);
        //handlerMapping组件
        initHandlerMappings(context);
        //参数适配器组件
        initHandlerAdapters(context);
        //异常拦截器组件
        initHandlerExceptionResolvers(context);
        //视图处理器
        initRequestToViewNameTranslator(context);
        //视图解析器
        initViewResolvers(context);
        //flashMap——参数缓存器
        initFlashMapManager(context);
    }

    private void initFlashMapManager(SelfApplicationContext context) {

    }

    /**
     * 初始化视图解析器
     * @param context
     */
    private void initViewResolvers(SelfApplicationContext context) {

        //contextType决定返回给前端的数据格式
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath=this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);

        for(File template:templateRootDir.listFiles()){
            this.viewResolvers.add(new SelfViewResolver(templateRoot));
        }

    }

    private void initRequestToViewNameTranslator(SelfApplicationContext context) {

    }

    private void initHandlerExceptionResolvers(SelfApplicationContext context) {

    }

    /**
     * 将request请求变成handler
     *
     * @param context
     */
    private void initHandlerAdapters(SelfApplicationContext context) {
        //将一个request请求变成一个Handler，参数都是字符串的，如何匹配handler中的形式参数

        //因此需要拿到HandlerMapping才能做处理

        //意味着有几个HandlerMapping就有几个HandlerAdapter

        for(SelfHandlerMapping handlerMapping:this.handlerMappings){
            //将一个HandlerMapping和handlerAdpater建立关系
            this.handlerAdapters.put(handlerMapping,new SelfHandlerAdapter());

        }
    }

    private void initHandlerMappings(SelfApplicationContext context) {
        String[] beanNames = context.getBeanDefinitionNames();
        try{
            for(String beanName:beanNames){
                Object controller = context.getBean(beanName);
                Class<?> clazz = controller.getClass();
                if(!clazz.isAnnotationPresent(SelfController.class)){//如果没有SelfController注解
                    continue;
                }

                String baseUrl = "";
                if(clazz.isAnnotationPresent(SelfRequestMapping.class)){//判断是不是有SelfRequestMapping注解
                    SelfRequestMapping selfRequestMapping = clazz.getAnnotation(SelfRequestMapping.class);
                    baseUrl = selfRequestMapping.value();
                }

                //默认获取所有的public方法
                for(Method method:clazz.getMethods()){
                    if(!method.isAnnotationPresent(SelfRequestMapping.class)){
                        continue;
                    }

                    SelfRequestMapping requestMapping = method.getAnnotation(SelfRequestMapping.class);
                    String url = ("/"+baseUrl+"/"+requestMapping.value()).replaceAll("/+","/");
                    Pattern pattern = Pattern.compile(url);
                    this.handlerMappings.add(new SelfHandlerMapping(controller,method,pattern));//38:28
                    System.out.println("Mapped:"+url+":"+method);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initLocaleResolver(SelfApplicationContext context) {

    }

    private void initThemeResolver(SelfApplicationContext context) {

    }

    private void initMultipartResolver(SelfApplicationContext context) {

    }


}
