package com.learn.springioc.framework.webmvc.servlet;

import java.util.Map;

/**
 * autor:liman
 * createtime:2019/5/27
 * comment: 模拟的ModelAndView
 */
public class SelfModelAndView {

    private String viewName;
    private Map<String,?> model;

    public SelfModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public SelfModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, ?> getModel() {
        return model;
    }

    public void setModel(Map<String, ?> model) {
        this.model = model;
    }
}
