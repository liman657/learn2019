package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.pmp.model.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/23
 * comment:
 */
public interface IDeptService extends IService<SysDeptEntity> {

    public List<SysDeptEntity> queryAll(Map<String, Object> map);

    public List<Long> queryDeptByParentId(Long parentId);

    /**
     * 底层递归获取该部门id的所有子部门信息
     * @param deptId
     * @return
     */
    public List<Long> getSubDeptIdList(Long deptId);

}
