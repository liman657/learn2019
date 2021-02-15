package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.pmp.model.entity.SysMenuEntity;
import com.liman.learn.pmp.model.entity.SysRoleMenuEntity;
import com.liman.learn.pmp.model.mapper.SysMenuDao;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.server.IMenuService;
import com.liman.learn.pmp.server.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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

    /**
     * 获取用户左边导航的菜单数据
     * @param currUserId
     * @return
     */
    @Override
    public List<SysMenuEntity> getUserMenuList(Long currUserId) {
        List<SysMenuEntity> menuList = Lists.newLinkedList();
        if(currUserId == Constant.SUPER_ADMIN){//如果是管理员，则返回所有一级菜单数据
            menuList = getAllMenuList(null);
        }else{
            //非超级管理员的情况 。 根据分配给用户角色-菜单的关联信息来获取
            List<Long> userMenuIdList = sysUserDao.queryAllUserMenuInfoByUserId(currUserId);
            menuList=getAllMenuList(userMenuIdList);
        }
        return menuList;
    }

    /**
     * 获取所有菜单列表
     * @param menuIdList
     * @return
     */
    public List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
        List<SysMenuEntity> allMenuInfoList = queryFirstLevelMenuList(0L, menuIdList);

        //TODO:这里需要通过递归的操作，获取一级菜单下的所有菜单数据
        getMenuTrees(allMenuInfoList,menuIdList);

        return allMenuInfoList;
    }

    /**
     * 查询指定用户下的一级菜单数据。
     * 根据父菜单id查询子菜单列表 ~ 找出一级菜单列表(找出类型为 目录 的菜单)
     * @param parentMenuId
     * @param menuIdList 这个是针对非管理员的查询菜单列表的数据。这个只是一个有菜单id的列表，需要根据每一个id换成对应的菜单对象。
     * @return
     */
    private List<SysMenuEntity> queryFirstLevelMenuList(Long parentMenuId,List<Long> menuIdList){
        //先查出所有的一级菜单
        List<SysMenuEntity> allParentMenuList = baseMapper.queryListParentId(parentMenuId);

        //如果为空，说明是管理员，则直接返回所有父菜单数据
        if(CollectionUtils.isEmpty(menuIdList)){
            return allParentMenuList;
        }

        //如果不为空，则表示是要查询普通用户菜单数据。
        List<SysMenuEntity> userMenuList = Lists.newLinkedList();
        for(SysMenuEntity menuEntity:allParentMenuList){
            if(menuIdList.contains(menuEntity.getMenuId())){
                userMenuList.add(menuEntity);
            }
        }
        return userMenuList;
    }

    /**
     * 递归查询菜单数据
     * @param menuList
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getMenuTrees(List<SysMenuEntity> menuList,List<Long> menuIdList){
        List<SysMenuEntity> subMenuListResult = Lists.newLinkedList();
        List<SysMenuEntity> tempMenuList;
        for(SysMenuEntity menuEntity : menuList){
//            if(menuEntity.getType() == Constant.MenuType.CATALOG.getValue()){//如果是目录
//                tempMenuList = queryFirstLevelMenuList(menuEntity.getMenuId(),menuIdList);
//                menuEntity.setSubMenuList(getMenuTrees(tempMenuList,menuIdList));
//            }

            tempMenuList = queryFirstLevelMenuList(menuEntity.getMenuId(),menuIdList);
            if(CollectionUtils.isNotEmpty(tempMenuList)){
                menuEntity.setSubMenuList(getMenuTrees(tempMenuList,menuIdList));
            }
            subMenuListResult.add(menuEntity);
        }

        return subMenuListResult;
    }
}
