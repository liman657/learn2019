package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.pmp.model.entity.SysRoleMenuEntity;
import com.liman.learn.pmp.model.mapper.SysRoleMenuDao;
import com.liman.learn.pmp.server.IRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment: 角色-菜单关联信息处理服务类
 */
@Service("roleMenuService")
@Slf4j
public class RoleMenuServiceImpl extends ServiceImpl<SysRoleMenuDao,SysRoleMenuEntity> implements IRoleMenuService {

    /**
     * 维护角色-菜单关联信息
     * @param roleId
     * @param menuIdList
     */
    @Override
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {

        //保存之前需要删除相关关联信息
        deleteBatch(Arrays.asList(roleId));

        //保存角色-菜单关联信息
        SysRoleMenuEntity roleMenuEntity;
        if(menuIdList!=null && !menuIdList.isEmpty()){
            for(Long menuId:menuIdList){
                roleMenuEntity = new SysRoleMenuEntity();
                roleMenuEntity.setRoleId(roleId);
                roleMenuEntity.setMenuId(menuId);
                this.save(roleMenuEntity);
            }
        }
    }

    /**
     * 批量删除与当前角色关联的部门信息
     * @param roleIds
     */
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if(null!=roleIds && !roleIds.isEmpty()){
            String toDelRoleIds = Joiner.on(",").join(roleIds);
            baseMapper.deleteBatch(CommonUtil.concatStrToInt(toDelRoleIds,","));
        }
    }

    @Override
    public List<Long> queryMenuByRoleIdList(Long roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }
}
