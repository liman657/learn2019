package com.liman.learn.pmp.server;

/**
 * autor:liman
 * createtime:2021/1/4
 * comment:
 */
public interface IUserService {

    public boolean updatePassword(Long userId,String oldPassword,String newPassword);

}
