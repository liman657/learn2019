package com.learn.netty_im.service;

import com.learn.netty_im.domain.TChatMsg;
import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.dto.FriendRequestVO;
import com.learn.netty_im.dto.MyFriendsVO;
import com.learn.netty_im.websocket.ChatMsg;
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

    /**
     * 删除好友请求记录
     * @param sendUserId
     * @param acceptUserId
     */
    public void deleteFriendRequest(String sendUserId,String acceptUserId);

    /**
     * 通过好友请求
     * @param sendUserId
     * @param acceptUserId
     * @Description:
     *  1、保存好友
     *  2、逆向保存好友
     *  3、删除好友的请求记录
     */
    public void passFriendRequest(String sendUserId,String acceptUserId);

    /**
     * 查询好友列表
     * @param userId
     * @return
     */
    public List<MyFriendsVO> queryMyFriends(String userId);

    /**
     * 保存用户发送的消息到数据库
     * @param chatMsg
     * @return
     */
    public String saveMsg(TChatMsg chatMsg);

    /**
     * 批量签收消息
     * @param msgIds
     * @return
     */
    public void udpateMsgSigned(List<String> msgIds);

    /**
     * 获取未签收的消息列表
     * @param acceptUserId
     * @return
     */
    public List<ChatMsg> getUnReadMsgList(String acceptUserId);
}
