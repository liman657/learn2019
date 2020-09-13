package com.learn.springauthserver.service;

import com.learn.springauthmodel.entity.User;
import com.learn.springauthmodel.mapper.UserMapper;
import com.learn.springauthserver.dto.AccessTokenDto;
import com.learn.springauthserver.dto.UpdatePsdDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:user的公共服务
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 数据库层面认证用户是否存在
     * @param userName
     * @param password
     * @return
     */
    public User authUser(String userName, String password){
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            throw new RuntimeException("用户名或密码不能为空");
        }
        User user = userMapper.selectByUserName(userName);
        if(user==null){
            throw new RuntimeException("当前用户不存在");
        }

        if(!password.equals(user.getPassword())){
            throw new RuntimeException("用户名密码不匹配");
        }
        return user;
    }

    public User selectUserByUserName(String userName){
        return userMapper.selectByUserName(userName);
    }

    /**
     * 修改密码
     * @param tokenDto
     * @param updatePsdDto
     * @return
     */
    public int updatePassword(AccessTokenDto tokenDto, UpdatePsdDto updatePsdDto){
        return userMapper.updatePassword(tokenDto.getUserName(),updatePsdDto.getOldPassword(),updatePsdDto.getNewPassword());
    }

    /**
     * 直接暴露底层接口
     * @param userName
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public int updatePassword(String userName,String oldPwd,String newPwd){
        return userMapper.updatePassword(userName, oldPwd, newPwd);
    }

    public int addUser(User user){
        return userMapper.insert(user);
    }
}