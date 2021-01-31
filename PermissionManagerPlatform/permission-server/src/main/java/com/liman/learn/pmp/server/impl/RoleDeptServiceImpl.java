package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.pmp.model.entity.SysRoleDeptEntity;
import com.liman.learn.pmp.model.mapper.SysRoleDeptDao;
import com.liman.learn.pmp.server.IRoleDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:角色-部门关联信息处理服务类
 */
@Service("roleDeptService")
@Slf4j
public class RoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao,SysRoleDeptEntity> implements IRoleDeptService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Long roleId, List<Long> deptIdList) {
        //需要先清除旧的关联数据，再插入新的关联信息
        deleteBatch(Arrays.asList(roleId));

        SysRoleDeptEntity entity;
        if (deptIdList!=null && !deptIdList.isEmpty()){
            for (Long depId:deptIdList){
                entity=new SysRoleDeptEntity();
                entity.setRoleId(roleId);
                entity.setDeptId(depId);
                this.save(entity);
            }
        }
    }

    @Override
    public void deleteBatch(List<Long> roleIds) {
        if (roleIds!=null && !roleIds.isEmpty()){
            String toDelRoleIds= Joiner.on(",").join(roleIds);
            baseMapper.deleteBatch(CommonUtil.concatStrToInt(toDelRoleIds,","));
        }
    }

    @Override
    public List<Long> queryDeptByRoleIdList(Long roleId) {
        return baseMapper.queryDeptIdListByRoleId(roleId);
    }
}
