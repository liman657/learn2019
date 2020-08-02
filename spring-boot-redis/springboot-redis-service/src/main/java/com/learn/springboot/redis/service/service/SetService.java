package com.learn.springboot.redis.service.service;

import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.User;
import com.learn.springboot.redis.model.mapper.UserMapper;
import com.learn.springboot.redis.service.constants.RedisKeyConstants;
import com.learn.springboot.redis.service.service.redis.SetRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * autor:liman
 * createtime:2020/7/29
 * comment:以用户注册为实例场景，操作set集合的业务类
 */
@Slf4j
@Service
public class SetService {

    @Autowired
    private SetRedisService setRedisService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer registerUser(User user){
        if(this.exist(user)){
            throw new RuntimeException(StatusCode.UserEmailHasExist.getMsg());
        }
        int res = userMapper.insertSelective(user);
        if(res>0){
            setRedisService.setUserEmail(user);
        }
        return user.getId();
    }

    /**
     * 判断user的email是否存在
     * @param user
     * @return
     */
    private Boolean exist(final User  user){
        boolean isExist = setRedisService.isUserExistEmail(user);
        if(isExist){
            return true;
        }else{
            User newUser = userMapper.selectByEmail(user.getEmail());
            if(null!=newUser){
                setRedisService.setUserEmail(newUser);
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 获取缓存中用户的邮箱信息
     * @param userId
     * @return
     */
    public Set<String> getEmails(int userId){
        Set<String> emails = new HashSet<>();
        try {
            emails = setRedisService.getEmails(userId);
        } catch (Exception e) {
            log.error("获取用户注册邮箱异常，异常信息为:{}",e);
        }
        return emails;
    }

}
