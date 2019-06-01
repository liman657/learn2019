package com.learn.springioc.framework.webmvc.servlet;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.Locale;

/**
 * autor:liman
 * createtime:2019/5/27
 * comment: 视图解析器
 */
public class SelfViewResolver {

    private final String DEFAULT_TEMPLATE_SUFFIX=".html";

    private File templateRootDir;

    private String viewName;

    public SelfViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        templateRootDir = new File(templateRootPath);
    }

    public SelfView resolveViewName(String viewName,Locale locale) throws Exception{

        if(StringUtils.isBlank(viewName)){
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX)?viewName:(viewName+DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath()+"/"+viewName).replaceAll("/+","/"));


        return new SelfView(templateFile);
    }
}
