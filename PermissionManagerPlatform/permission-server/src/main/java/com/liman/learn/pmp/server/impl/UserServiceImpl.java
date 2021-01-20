package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.server.IUserService;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2021/1/4
 * comment:用户的业务类，这里用的是MybatisPlus，不用注入我们自己写的dao
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<SysUserDao,SysUserEntity> implements IUserService {

    public boolean updatePassword(Long userId,String oldPassword,String newPassword){
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);

        boolean updateRes = this.update(userEntity, new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", oldPassword));
        return updateRes;
    }

}
