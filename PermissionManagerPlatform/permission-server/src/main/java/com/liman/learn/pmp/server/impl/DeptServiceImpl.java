package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.pmp.model.entity.SysDeptEntity;
import com.liman.learn.pmp.model.mapper.SysDeptDao;
import com.liman.learn.pmp.server.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/23
 * comment:
 */
@Service("deptService")
@Slf4j
public class DeptServiceImpl extends ServiceImpl<SysDeptDao,SysDeptEntity> implements IDeptService {

    @Override
    public List<SysDeptEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }

    @Override
    public List<Long> queryDeptByParentId(Long parentId) {
        return baseMapper.queryDeptByParentId(parentId);
    }


}
