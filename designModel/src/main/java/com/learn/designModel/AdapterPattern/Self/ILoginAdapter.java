package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment:
 */
public interface ILoginAdapter {

    /**
     * 判断接口是否兼容
     * @param adapter
     * @return
     */
    boolean support(Object adapter);

    ResultMsg login(String username,String password);
}