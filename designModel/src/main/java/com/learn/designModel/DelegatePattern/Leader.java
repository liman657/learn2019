package com.learn.designModel.DelegatePattern;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment: 项目组长的角色
 */
public class Leader {

    private Map<String,IEmployee> employeeMap = new HashMap<String,IEmployee>();

    public Leader(){
        employeeMap.put("code",new EmployeeCoder());
        employeeMap.put("test",new EmployeeTest());
    }

    public void doSomething(String command){
        //如果不用map，则这里会有很多的if...else语句，这个是很痛苦的一件事情
        employeeMap.get(command).doWork();
    }

}
