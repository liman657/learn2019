package com.learn.springmvc.controller;

import com.learn.springmvc.Annotation.SelfAutowired;
import com.learn.springmvc.Annotation.SelfController;
import com.learn.springmvc.Annotation.SelfRequestMapping;
import com.learn.springmvc.Annotation.SelfRequestParam;
import com.learn.springmvc.service.IDemoService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//虽然，用法一样，但是没有功能
@SelfController
@SelfRequestMapping("/demo")
public class DemoController {

  	@SelfAutowired
	private IDemoService demoService;

	@SelfRequestMapping("/query")
	public void query(HttpServletRequest req, HttpServletResponse resp,
					  @SelfRequestParam("name") String name){
		String result = "My name is " + name;
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SelfRequestMapping("/add")
	public void add(HttpServletRequest req, HttpServletResponse resp,
					@SelfRequestParam("a") Integer a, @SelfRequestParam("b") Integer b){
		try {
			resp.getWriter().write(a + "+" + b + "=" + (a + b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SelfRequestMapping("/sub")
	public void add(HttpServletRequest req, HttpServletResponse resp,
					@SelfRequestParam("a") Double a, @SelfRequestParam("b") Double b){
		try {
			resp.getWriter().write(a + "-" + b + "=" + (a - b));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SelfRequestMapping("/remove")
	public String  remove(@SelfRequestParam("id") Integer id){
		return "" + id;
	}

}
