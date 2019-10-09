package com.learn.springboot_mq.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/30.
 */
public class LogDto implements Serializable{

    private String methodName;

    private String operateData;

    public LogDto() {
    }

    public LogDto(String methodName, String operateData) {
        this.methodName = methodName;
        this.operateData = operateData;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOperateData() {
        return operateData;
    }

    public void setOperateData(String operateData) {
        this.operateData = operateData;
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "methodName='" + methodName + '\'' +
                ", operateData='" + operateData + '\'' +
                '}';
    }
}