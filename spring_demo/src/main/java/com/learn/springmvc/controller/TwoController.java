package com.learn.springmvc.controller;

import com.learn.springmvc.service.IDemoService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TwoController {
	
	private IDemoService demoService;

	public void edit(HttpServletRequest req,HttpServletResponse resp,
					 String name){
		String result = demoService.get(name);
		try {
			resp.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
