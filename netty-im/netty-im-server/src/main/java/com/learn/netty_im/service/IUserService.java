package com.learn.netty_im.service;

import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.dto.FriendRequestVO;
import com.learn.netty_im.pojo.requsetentity.UserRequest;

import java.util.List;

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

    public Integer checkAddFriend(String userId,String friendUserName);

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    public TUsers queryUserInfoByUserName(String userName);

    /**
     * 发送添加好友请求
     * @param myUserId
     * @param friendUsername
     */
    public void sendFriendRequest(String myUserId, String friendUsername);

    /**
     * 查询接收到的好友请求
     * @param userId
     */
    public List<FriendRequestVO> queryFriendRequestList(String userId);
}
