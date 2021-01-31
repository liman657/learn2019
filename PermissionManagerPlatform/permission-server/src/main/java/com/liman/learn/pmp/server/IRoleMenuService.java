package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysRoleMenuEntity;

import javax.imageio.spi.IIOServiceProvider;
import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:
 */
public interface IRoleMenuService extends IService<SysRoleMenuEntity> {

    void saveOrUpdate(Long roleId, List<Long> menuIdList);
//
    void deleteBatch(List<Long> roleIds);
//
    List<Long> queryMenuByRoleIdList(Long roleId);

}
