package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.pmp.model.entity.SysMenuEntity;
import com.liman.learn.pmp.model.entity.SysRoleMenuEntity;
import com.liman.learn.pmp.model.mapper.SysMenuDao;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.server.IMenuService;
import com.liman.learn.pmp.server.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/25
 * comment: 菜单服务类
 */
@Service("menuServie")
@Slf4j
public class MenuServiceImpl extends ServiceImpl<SysMenuDao,SysMenuEntity> implements IMenuService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private IRoleMenuService roleMenuService;

    @Override
    public List<SysMenuEntity> queryAll() {
        return baseMapper.queryList();
    }

    /**
     * 这个查询服务于添加或则修改页面中加载父节点的zTree控件加载数据，查询的时候排除了按钮类型
     * @return
     */
    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> queryByParentId(Long menuId) {
        return baseMapper.queryListParentId(menuId);
    }

    /**
     * 删除菜单的时候，同时需要删除菜单角色表中的数据
     * @param menuId
     */
    @Override
    public void delete(Long menuId) {
        removeById(menuId);
        //删除角色菜单表中的数据
        roleMenuService.remove(new QueryWrapper<SysRoleMenuEntity>().eq("menu_id",menuId));
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(Long currUserId) {
        return null;
    }
}
