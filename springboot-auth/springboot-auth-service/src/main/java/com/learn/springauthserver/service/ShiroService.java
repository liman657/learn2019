package com.learn.springauthserver.service;

import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.dto.UpdatePsdDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * autor:liman
 * createtime:2019/12/24
 * comment:
 */
@Service
@Slf4j
public class ShiroService {

    @Autowired
    private UserService userService;

    //修改密码
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(final UpdatePsdDto dto)throws Exception{
        Subject subject=SecurityUtils.getSubject();

        if (subject!=null && subject.getPrincipal()!=null){
            User currUser= (User) subject.getPrincipal();//从认证主体中取出用户信息，类似从token中取出用户信息

            //核心业务逻辑：修改密码
            User user=userService.selectUserByUserName(currUser.getUserName());
            if (user==null){
                throw new RuntimeException("当前用户不存在，修改密码失败！");
            }
            if (!user.getPassword().equals(dto.getOldPassword())){
                throw new RuntimeException("旧密码不匹配！");
            }
            //修改密码
            int res=userService.updatePassword(currUser.getUserName(),dto.getOldPassword(),dto.getNewPassword());
            if (res<=0){
                throw new RuntimeException("修改密码失败~请重新尝试或者联系管理员！");
            }

            this.invalidateSession();
        }
    }

    //失效session
    public void invalidateSession() throws Exception{
        Subject subject=SecurityUtils.getSubject();
        if (subject!=null){
            subject.logout();
        }
    }

}
