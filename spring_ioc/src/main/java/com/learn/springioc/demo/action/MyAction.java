package com.learn.springioc.demo.action;

import com.learn.springioc.demo.service.IModifyService;
import com.learn.springioc.demo.service.IQueryService;
import com.learn.springioc.framework.annotation.SelfAutowired;
import com.learn.springioc.framework.annotation.SelfController;
import com.learn.springioc.framework.annotation.SelfRequestMapping;
import com.learn.springioc.framework.annotation.SelfRequestParam;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 公布接口url
 * @author Tom
 *
 */
@SelfController
@SelfRequestMapping("/web")
public class MyAction {

	@SelfAutowired
	IQueryService queryService;
	@SelfAutowired
	IModifyService modifyService;

	@SelfRequestMapping("/query.json")
	public void query(HttpServletRequest request, HttpServletResponse response,
								@SelfRequestParam("name") String name){
		String result = queryService.query(name);
		out(response,result);
	}
	
	@SelfRequestMapping("/add*.json")
	public void add(HttpServletRequest request,HttpServletResponse response,
			   @SelfRequestParam("name") String name,@SelfRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		out(response,result);
	}
	
	@SelfRequestMapping("/remove.json")
	public void remove(HttpServletRequest request,HttpServletResponse response,
		   @SelfRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		out(response,result);
	}
	
	@SelfRequestMapping("/edit.json")
	public void edit(HttpServletRequest request,HttpServletResponse response,
			@SelfRequestParam("id") Integer id,
			@SelfRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		out(response,result);
	}
	
	
	
	private void out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
