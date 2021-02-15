package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysUserRoleEntity;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:
 */
public interface IUserRoleService extends IService<SysUserRoleEntity> {

    void deleteBatch(List<Long> roleIds);

    public void saveOrUpdate(Long userId, List<Long> roleIds);

    public List<Long> queryRoleIdList(Long userId);

}
