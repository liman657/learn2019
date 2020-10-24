package com.learn.netty_im.service.impl;

import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.idworker.Sid;
import com.learn.netty_im.mapper.TUsersMapper;
import com.learn.netty_im.pojo.requsetentity.UserRequest;
import com.learn.netty_im.service.IUserService;
import com.learn.netty_im.utils.FastDFSClient;
import com.learn.netty_im.utils.FileUtils;
import com.learn.netty_im.utils.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;

/**
 * autor:liman
 * createtime:2020/9/28
 * comment:userService的实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private TUsersMapper userMapper;

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
     * @param userName
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isUserExist(String userName) {
        TUsers tUsers = userMapper.selectByUsernameAndPassword(userName, null);
        return tUsers != null ? true : false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TUsers queryUserForLogin(String username,String password){
        TUsers tUsers = userMapper.selectByUsernameAndPassword(username, password);
        return tUsers;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TUsers saveUser(UserRequest users){
        TUsers toSaveUser = new TUsers();
        String qrCodePathPrefix = "E:/IMNettyFile/userQrcode/";
        try {
            BeanUtils.copyProperties(toSaveUser,users);
            String userId = sid.nextShort();
            String qrFileUrl = qrCodePathPrefix+userId+"_qrcode.png";
            qrCodeUtils.createQRCode(qrFileUrl,"manxin_qrcode:"+ users.getUsername());

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
            BeanUtils.copyProperties(toSaveUser,userRequest);
            userMapper.updateByPrimaryKey(toSaveUser);
            result=selectUserById(userRequest.getId());
        } catch (Exception e) {
            log.error("更新用户数据异常，异常信息为:{}",e);
        }
        return result;
    }

    public TUsers selectUserById(String userId){
        return userMapper.selectByPrimaryKey(userId);
    }

}
