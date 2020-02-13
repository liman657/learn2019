package com.learn.springbootjvm.controller;

import com.learn.springbootjvm.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆上分配对象
 */
@RestController
public class HeapController {
    List<Person> list=new ArrayList<Person>();
    @GetMapping("/heap")
    public String heap() throws Exception{
        while(true){
//            System.out.println("test add object");
            list.add(new Person());
            Thread.sleep(1);
        }
    }
}
