package com.learn.netty_im.service;

import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.pojo.requsetentity.UserRequest;

/**
 * autor:liman
 * createtime:2020/9/28
 * comment:
 */
public interface IUserService {

    public boolean isUserExist(String userName);

    public TUsers queryUserForLogin(String username, String password);

    public TUsers saveUser(UserRequest userRequest);

    /**
     * 修改用户记录
     * @param userRequest
     */
    public TUsers updateUserInfo(UserRequest userRequest);

}
