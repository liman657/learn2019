package com.learn.designModel.AdapterPattern;

/**
 * autor:liman
 * comment: 这个接口可以没有
 */
public interface LoginAdapter {
    /**
     * 判断是否兼容
     * @param adapter
     * @return
     */
    boolean support(Object adapter);

    /**
     * 登录接口
     * @param id
     * @param adapter
     * @return
     */
    ResultMsg login(String id,Object adapter);
}
