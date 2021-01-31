package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysMenuEntity;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/25
 * comment:
 */
public interface IMenuService extends IService<SysMenuEntity> {

    List<SysMenuEntity> queryAll();

    List<SysMenuEntity> queryNotButtonList();//查询非按钮资源

    List<SysMenuEntity> queryByParentId(Long menuId);

    void delete(Long menuId);

    List<SysMenuEntity> getUserMenuList(Long currUserId);

}
