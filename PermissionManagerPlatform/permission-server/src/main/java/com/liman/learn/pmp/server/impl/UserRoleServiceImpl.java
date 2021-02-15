package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.pmp.model.entity.SysUserRoleEntity;
import com.liman.learn.pmp.model.mapper.SysUserRoleDao;
import com.liman.learn.pmp.server.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:用户角色服务类
 */
@Service("userRoleService")
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements IUserRoleService {
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds != null && !roleIds.isEmpty()) {
            String delIds = Joiner.on(",").join(roleIds);
            baseMapper.deleteBatch(CommonUtil.concatStrToInt(delIds, ","));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long userId, List<Long> roleIds) {
        //需要先清除旧的关联数据，再插入新的关联信息
        this.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

        if (roleIds != null && !roleIds.isEmpty()) {
            SysUserRoleEntity entity;
            for (Long rId : roleIds) {
                entity = new SysUserRoleEntity();
                entity.setRoleId(rId);
                entity.setUserId(userId);
                this.save(entity);
            }
        }
    }

    //获取分配给用户的角色id列表
    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return baseMapper.queryRoleIdList(userId);
    }
}