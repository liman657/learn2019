package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.SysRoleEntity;
import com.liman.learn.pmp.model.mapper.SysRoleDao;
import com.liman.learn.pmp.server.IRoleDeptService;
import com.liman.learn.pmp.server.IRoleMenuService;
import com.liman.learn.pmp.server.IRoleService;
import com.liman.learn.pmp.server.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment: 角色服务类
 */
@Service("roleService")
@Slf4j
public class RoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements IRoleService {

    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IRoleDeptService roleDeptService;
    @Autowired
    private IUserRoleService userRoleService;

    @Override
    public PageUtil queryPage(Map<String, Object> map) {
        String search = (map.get("search") != null) ? (String) map.get("search") : "";
        IPage<SysRoleEntity> iPage = new QueryUtil<SysRoleEntity>().getQueryPage(map);
        QueryWrapper<SysRoleEntity> wrapper = new QueryWrapper<SysRoleEntity>()
                .like(StringUtils.isNotBlank(search), "role_name", search);

        IPage<SysRoleEntity> resPage = this.page(iPage, wrapper);
        return new PageUtil(resPage);
    }

    /**
     * 保存用户角色信息 需要加上事务控制
     * @param role
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {

        role.setCreateTime(DateTime.now().toDate());
        this.save(role);

        //插入角色-菜单关联信息
        roleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());

        //插入角色-部门关联信息
        roleDeptService.saveOrUpdate(role.getRoleId(),role.getDeptIdList());


    }

    /**
     * 修改菜单相关信息
     * @param role
     */
    @Override
    public void updateRole(SysRoleEntity role) {
        //更新角色表本身的信息
        this.updateById(role);

        //更新角色-菜单关联信息
        roleMenuService.saveOrUpdate(role.getRoleId(),role.getMenuIdList());
        //更新角色-部门关联信息
        roleDeptService.saveOrUpdate(role.getRoleId(),role.getDeptIdList());
    }

    /**
     * 批量删除角色信息
     * @param ids
     */
    @Override
    public void deleteBatch(Long[] ids) {
        List<Long> roleIds=Arrays.asList(ids);
        this.removeByIds(roleIds);

        //删除角色-菜单关联信息
        roleMenuService.deleteBatch(roleIds);
        //删除角色-部门关联信息
        roleDeptService.deleteBatch(roleIds);
        //删除角色-用户关联信息
        userRoleService.deleteBatch(roleIds);
    }
}
