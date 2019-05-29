package com.learn.springioc.demo.service.impl;


import com.learn.springioc.demo.service.IModifyService;
import com.learn.springioc.framework.annotation.SelfService;

/**
 * 增删改业务
 * @author Tom
 *
 */
@SelfService
public class ModifyService implements IModifyService {

	/**
	 * 增加
	 */
	public String add(String name,String addr) throws Exception {
		throw new Exception("故意抛出的异常");
//		return "modifyService add,name=" + name + ",addr=" + addr;
	}

	/**
	 * 修改
	 */
	public String edit(Integer id,String name) {
		return "modifyService edit,id=" + id + ",name=" + name;
	}

	/**
	 * 删除
	 */
	public String remove(Integer id) {
		return "modifyService id=" + id;
	}
	
}
