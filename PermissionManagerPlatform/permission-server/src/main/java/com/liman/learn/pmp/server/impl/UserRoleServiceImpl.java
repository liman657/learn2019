package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.pmp.model.entity.SysUserRoleEntity;
import com.liman.learn.pmp.model.mapper.SysUserRoleDao;
import com.liman.learn.pmp.server.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:用户角色服务类
 */
@Service("userRoleService")
@Slf4j
public class UserRoleServiceImpl extends ServiceImpl<SysUserRoleDao,SysUserRoleEntity> implements IUserRoleService {
    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds!=null && !roleIds.isEmpty()){
            String delIds= Joiner.on(",").join(roleIds);
            baseMapper.deleteBatch(CommonUtil.concatStrToInt(delIds,","));
        }
    }
}
