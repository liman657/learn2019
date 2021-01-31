package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysRoleDeptEntity;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:
 */
public interface IRoleDeptService extends IService<SysRoleDeptEntity> {
    void saveOrUpdate(Long roleId, List<Long> deptIdList);

    void deleteBatch(List<Long> roleIds);

    List<Long> queryDeptByRoleIdList(Long roleId);
}
