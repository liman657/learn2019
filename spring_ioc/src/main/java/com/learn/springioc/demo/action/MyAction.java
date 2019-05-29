package com.learn.springioc.demo.action;

import com.learn.springioc.demo.service.IModifyService;
import com.learn.springioc.demo.service.IQueryService;
import com.learn.springioc.framework.annotation.SelfAutowired;
import com.learn.springioc.framework.annotation.SelfController;
import com.learn.springioc.framework.annotation.SelfRequestMapping;
import com.learn.springioc.framework.annotation.SelfRequestParam;
import com.learn.springioc.framework.webmvc.servlet.SelfModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
	public SelfModelAndView query(HttpServletRequest request, HttpServletResponse response,
								  @SelfRequestParam("name") String name){
		String result = queryService.query(name);
		return out(response,result);
	}
	
	@SelfRequestMapping("/add*.json")
	public SelfModelAndView add(HttpServletRequest request,HttpServletResponse response,
			   @SelfRequestParam("name") String name,@SelfRequestParam("addr") String addr){
		String result = null;
		try {
			result = modifyService.add(name,addr);
		} catch (Exception e) {
//			e.printStackTrace();
			Map<String,Object> model = new HashMap<>();
			model.put("detail",e.getCause().getMessage());
			model.put("stackTrace",e.getCause().getStackTrace());
			return new SelfModelAndView("500",model);//42:00
		}
		return out(response,result);
	}
	
	@SelfRequestMapping("/remove.json")
	public SelfModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @SelfRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@SelfRequestMapping("/edit.json")
	public SelfModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@SelfRequestParam("id") Integer id,
			@SelfRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private SelfModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public IQueryService getQueryService() {
		return queryService;
	}
}
