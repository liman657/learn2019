package com.learn.springbootjvm.controller;

import com.learn.springbootjvm.utils.MetaspaceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 在方法区分配，元空间工具类中会频繁的构建Class对象
 */
@RestController
public class NonHeapController {

    List<Class<?>> list=new ArrayList<Class<?>>();
    @GetMapping("/nonheap")
    public String heap() throws Exception{
        while(true){
            list.addAll(MetaspaceUtil.createClasses());
            Thread.sleep(5);
        }
    }
}
