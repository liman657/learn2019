package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysUserPostEntity;
import com.liman.learn.pmp.model.entity.SysUserRoleEntity;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:
 */
public interface IUserPostService extends IService<SysUserPostEntity> {

    void deleteBatch(List<Long> roleIds);

    public String getPostNameByUserId(Long userId);

    public void saveOrUpdate(Long userId, List<Long> postIds);

    public List<Long> queryPostIdList(Long userId);
}
