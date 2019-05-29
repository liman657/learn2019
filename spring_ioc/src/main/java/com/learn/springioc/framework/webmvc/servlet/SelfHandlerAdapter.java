package com.learn.springioc.framework.webmvc.servlet;

import com.learn.springioc.framework.annotation.SelfRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * createtime:2019/5/27
 * comment:
 */
public class SelfHandlerAdapter {

    public boolean support(Object handler) {
        return handler instanceof SelfHandlerMapping;
    }

    public SelfModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handlerMapping) throws Exception {

        SelfHandlerMapping handler = (SelfHandlerMapping) handlerMapping;

        //将方法的形参列表和request的参数列表进行一一对应
        Map<String, Integer> paramIndexMapping = new HashMap<String, Integer>();

        Method method = handler.getMethod();

        //提取方法中加了注解的参数
        Annotation[][] pa = method.getParameterAnnotations();
        for (int i = 0; i < pa.length; i++) {
            for (Annotation a : pa[i]) {
                if (a instanceof SelfRequestParam) {
                    String paramName = ((SelfRequestParam) a).value();
                    if (!"".equals(paramName.trim())) {
                        paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }

        //提取方法中的request和response参数
        Class<?>[] paramsTypes = method.getParameterTypes();
        for (int i = 0; i < paramsTypes.length; i++) {
            Class<?> type = paramsTypes[i];
            if (type == HttpServletRequest.class
                    || type == HttpServletResponse.class) {
                paramIndexMapping.put(type.getName(), i);
            }
        }


        //获取方法的形参列表
        Map<String, String[]> params = request.getParameterMap();

        //实参列表
        Object[] paramValues = new Object[paramsTypes.length];


        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(param.getValue()).
                    replaceAll("\\[|\\]", "").replaceAll("\\s", ",");

            if (!paramIndexMapping.containsKey(param.getKey())) {
                continue;
            }

            int index = paramIndexMapping.get(param.getKey());
            paramValues[index] = caseStringVal(paramsTypes[index], value);
        }

        if (paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = paramIndexMapping.get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = request;
        }

        if (paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            int respIndex = paramIndexMapping.get(HttpServletResponse.class.getName());
            paramValues[respIndex] = response;
        }

        Object returnValue = method.invoke(handler.getController(), paramValues);
        if (returnValue == null || returnValue instanceof Void) {
            return null;
        }

        //返回值处理，如果返回了ModelAndView，则进行相关处理，如果没有返回ModelAndView，则需要返回一个默认的ModelAndView
        boolean isModelAndView = handler.getMethod().getReturnType() == SelfModelAndView.class;

        if (isModelAndView) {
            return (SelfModelAndView) returnValue;
        }

        return null;
    }

    /**
     * 将任意类型的数据，转换成字符串类型
     *
     * @param paramsType
     * @param value
     * @return
     */
    private Object caseStringVal(Class<?> paramsType, String value) {

        if (String.class == paramsType) {
            return value;
        }

        if (Integer.class == paramsType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramsType) {
            return Double.valueOf(value);
        } else {
            return null;
        }
    }

}
