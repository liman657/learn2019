package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.SysRoleEntity;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:
 */
public interface IRoleService extends IService<SysRoleEntity> {

    PageUtil queryPage(Map<String,Object> map);

    void saveRole(SysRoleEntity role);

    void updateRole(SysRoleEntity role);

    void deleteBatch(Long[] ids);

}
