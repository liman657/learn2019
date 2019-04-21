package com.learn.springmvc.service.impl;


import com.learn.springmvc.Annotation.SelfService;
import com.learn.springmvc.service.IDemoService;

/**
 * 核心业务逻辑
 */
@SelfService
public class DemoService implements IDemoService {

	public String get(String name) {
		return "My name is " + name;
	}

}
