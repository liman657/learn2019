package com.learn.springioc.framework.webmvc.servlet;

import com.sun.istack.internal.Nullable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * autor:liman
 * createtime:2019/5/27
 * comment:
 */
public class SelfView {

    public final String DEFAULT_CONTENT_TYPE="text/html;charset=utf-8";

    private File viewFile;

    public SelfView(File viewFile){
        this.viewFile = viewFile;
    }

    /**
     * 渲染视图的方法
     * @param model
     * @param request
     * @param response
     * @throws Exception
     */
    public void render(@Nullable Map<String ,?> model,
                       HttpServletRequest request, HttpServletResponse response) throws Exception{
        StringBuffer sb = new StringBuffer();

        RandomAccessFile randomAccessFile = new RandomAccessFile(this.viewFile,"r");
        String line = null;
        while(null !=( line=randomAccessFile.readLine())){
            line = new String(line.getBytes("ISO-8859-1"),"utf-8");

            Pattern pattern = Pattern.compile("￥\\{[^\\}]+\\}", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                String paramName = matcher.group();
                paramName.replaceAll("￥\\{|\\}","");
                Object paramValue = model.get(paramName);
                if(null == paramValue){
                    continue;
                }
                line = matcher.replaceFirst(paramValue.toString());
                matcher = pattern.matcher(line);
            }
            sb.append(line);
        }

        response.setCharacterEncoding("utf-8");
//        response.setContentType(DEFAULT_CONTENT_TYPE);
        response.getWriter().write(sb.toString());
    }

}
