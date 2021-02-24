package com.learn.netty_im.service.impl;

import com.learn.netty_im.domain.TChatMsg;
import com.learn.netty_im.domain.TFriendsRequest;
import com.learn.netty_im.domain.TMyFriends;
import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.dto.FriendRequestVO;
import com.learn.netty_im.dto.MyFriendsVO;
import com.learn.netty_im.enums.MsgActionEnum;
import com.learn.netty_im.enums.MsgSignFlagEnum;
import com.learn.netty_im.enums.SearchFriendsStatusEnum;
import com.learn.netty_im.idworker.Sid;
import com.learn.netty_im.mapper.*;
import com.learn.netty_im.pojo.requsetentity.FriendsRequest;
import com.learn.netty_im.pojo.requsetentity.MyFriends;
import com.learn.netty_im.pojo.requsetentity.UserRequest;
import com.learn.netty_im.service.IUserService;
import com.learn.netty_im.utils.FastDFSClient;
import com.learn.netty_im.utils.FileUtils;
import com.learn.netty_im.utils.JsonUtils;
import com.learn.netty_im.utils.QRCodeUtils;
import com.learn.netty_im.websocket.ChatMsg;
import com.learn.netty_im.websocket.MsgContent;
import com.learn.netty_im.websocket.UserChannelRel;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/28
 * comment:userService的实现类
 */
@Service("userService")
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private TUsersMapper userMapper;
    @Autowired
    private TChatMsgMapper chatMsgMapper;

    @Autowired
    private TMyFriendsMapper tMyFriendsMapper;

    @Autowired
    private TFriendsRequestMapper tFriendsRequestMapper;

    @Autowired
    private TUsersMapperConsumer usersMapperConsumer;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private Sid sid;

    /**
     * 根据用户名判断用户是否存在
     *
     * @param userName
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isUserExist(String userName) {
        TUsers tUsers = userMapper.selectByUsernameAndPassword(userName, null);
        return tUsers != null ? true : false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TUsers queryUserForLogin(String username, String password) {
        TUsers tUsers = userMapper.selectByUsernameAndPassword(username, password);
        return tUsers;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TUsers saveUser(UserRequest users) {
        TUsers toSaveUser = new TUsers();
        String qrCodePathPrefix = "E:/IMNettyFile/userQrcode/";
        try {
            BeanUtils.copyProperties(toSaveUser, users);
            String userId = sid.nextShort();
            String qrFileUrl = qrCodePathPrefix + userId + "_qrcode.png";
            qrCodeUtils.createQRCode(qrFileUrl, "manxin_qrcode:" + users.getUsername());

            //上传二维码
            MultipartFile multipartQrcodeFile = FileUtils.fileToMultipart(qrFileUrl);

            String finalQrCodeFileUrl = fastDFSClient.uploadQRCode(multipartQrcodeFile);

            toSaveUser.setId(userId);
            toSaveUser.setQrcode(finalQrCodeFileUrl);
            toSaveUser.setCid(userId);
            userMapper.insert(toSaveUser);
            return toSaveUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public TUsers updateUserInfo(UserRequest userRequest) {
        TUsers toSaveUser = new TUsers();
        TUsers result = null;
        try {
            BeanUtils.copyProperties(toSaveUser, userRequest);
            userMapper.updateByPrimaryKey(toSaveUser);
            result = selectUserById(userRequest.getId());
        } catch (Exception e) {
            log.error("更新用户数据异常，异常信息为:{}", e);
        }
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Integer checkAddFriend(String userId, String friendUserName) {
        log.info("开始添加好友的检查项，参数为：{}，{}", userId, friendUserName);
        TUsers tUsers = queryUserInfoByUserName(friendUserName);
        //1、搜索的用户如果不存在，返回无此用户
        if (null == tUsers) {
            return SearchFriendsStatusEnum.USER_NOT_EXIST.status;
        }

        //2.搜索账号是你自己，返回[不能添加自己]
        if (tUsers.getId().equals(userId)) {
            return SearchFriendsStatusEnum.NOT_YOURSELF.status;
        }
        //3. 搜索的朋友已经是你的好友，返回[该用户已经是你的好友]
        TMyFriends tMyFriends = tMyFriendsMapper.selectMyFriendByUserIdAndFriendId(userId, tUsers.getId());
        if (null != tMyFriends) {
            return SearchFriendsStatusEnum.ALREADY_FRIENDS.status;
        }
        return SearchFriendsStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TUsers queryUserInfoByUserName(String userName) {
//        userMapper.selectUserInfoByUserName(userName);
        return userMapper.selectUserInfoByUserName(userName);

    }

    public TUsers selectUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendFriendRequest(String sendUserId, String friendUsername) {

        // 根据用户名把朋友信息查询出来
        TUsers friend = queryUserInfoByUserName(friendUsername);

        // 1. 查询发送好友请求记录表
        TFriendsRequest friendRequest = tFriendsRequestMapper.selectTFriendsRequestBySendUserIdAndAcceptUserId(sendUserId, friend.getId());
        if (friendRequest == null) {//如果不存在，才发起添加
            // 2. 如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
            String requestId = sid.nextShort();

            TFriendsRequest request = new TFriendsRequest();
            request.setId(requestId);
            request.setSendUserId(sendUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequestDateTime(new Date());
            tFriendsRequestMapper.insert(request);
        }
    }

    /**
     * 查询接收到的好友请求
     *
     * @param acceptUserId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId) {
        return usersMapperConsumer.queryFriendRequestList(acceptUserId);
    }

    /**
     * 删除好友申请的方法
     *
     * @param sendUserId
     * @param acceptUserId
     */
    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        TFriendsRequest request = new TFriendsRequest();
        request.setSendUserId(sendUserId);
        request.setAcceptUserId(acceptUserId);
        tFriendsRequestMapper.deleteBySendUserIdAndAcceptUserId(request);
    }

    /**
     * 通过好友申请的方法
     *
     * @param sendUserId
     * @param acceptUserId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        //先保存各自好友关系t_my_friends
        saveFriendsRequest(sendUserId, acceptUserId);
        saveFriendsRequest(acceptUserId, sendUserId);
        //然后删除好友申请记录t_friends_request
        deleteFriendRequest(sendUserId, acceptUserId);

        //这里需要将好友关系更新到发送方，使用websocket主动推送消息到请求发送者，更新他的通讯录列表
        Channel requestUserChannel = UserChannelRel.getUserChannel(sendUserId);
        if (null != requestUserChannel) {
            MsgContent msgContent = new MsgContent();
            msgContent.setAction(MsgActionEnum.PULL_FRIEND.type);
            requestUserChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(msgContent)));
        }
    }

    /**
     * @param sendUserId
     * @param acceptUserId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveFriendsRequest(String sendUserId, String acceptUserId) {
        TMyFriends myFriends = new TMyFriends();
        String recordId = sid.nextShort();
        myFriends.setId(recordId);
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        tMyFriendsMapper.insert(myFriends);
    }

    /**
     * 查询好友列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        return usersMapperConsumer.queryMyFriends(userId);
    }

    /**
     * 保存消息到数据库
     *
     * @param chatMsg
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveMsg(TChatMsg chatMsg) {
        String msgId = sid.nextShort();
        chatMsg.setId(msgId);
        chatMsg.setCreateTime(new Date());
        chatMsg.setSignFlag(MsgSignFlagEnum.unsign.type);//设置状态为未签收
        chatMsgMapper.insert(chatMsg);
        return msgId;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void udpateMsgSigned(List<String> msgIds) {
        chatMsgMapper.batchUpdateMsgSigned(msgIds);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public List<ChatMsg> getUnReadMsgList(String acceptUserId) {
        List<ChatMsg> unReadMsgList = new ArrayList<>();
        List<TChatMsg> tChatMsgs = chatMsgMapper.selectMsgListByAcceptIdAndFlag(acceptUserId, "0");
        if(CollectionUtils.isNotEmpty(tChatMsgs)){
            for(TChatMsg tChatMsg:tChatMsgs){
                ChatMsg chatMsg = new ChatMsg();
                chatMsg.setMsgId(tChatMsg.getId());
                chatMsg.setMsg(tChatMsg.getMsg());
                chatMsg.setAcceptUserId(tChatMsg.getAcceptUserId());
                chatMsg.setSendUserId(tChatMsg.getSendUserId());
                chatMsg.setSignFlag(tChatMsg.getSignFlag());
                unReadMsgList.add(chatMsg);
            }
        }
        return unReadMsgList;
    }
}
