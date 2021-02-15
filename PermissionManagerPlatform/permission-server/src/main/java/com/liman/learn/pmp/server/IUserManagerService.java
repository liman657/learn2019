package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.mchange.v2.ser.IndirectlySerialized;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:
 */
public interface IUserManagerService extends IService<SysUserEntity> {

    public PageUtil queryPage(Map<String, Object> map);

    public void saveUser(SysUserEntity entity);

    public void deleteUserBatch(Long[] ids);

    public void updateUser(SysUserEntity entity);

    public SysUserEntity getUserInfo(Long userId);

    public void resetPassword(Long[] ids);
}
