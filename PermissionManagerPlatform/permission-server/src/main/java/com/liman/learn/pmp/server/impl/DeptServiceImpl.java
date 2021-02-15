package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.pmp.model.entity.SysDeptEntity;
import com.liman.learn.pmp.model.mapper.SysDeptDao;
import com.liman.learn.pmp.server.ICommonDeptDataService;
import com.liman.learn.pmp.server.IDeptService;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ICommonDeptDataService commonDeptDataService;

    @Override
    public List<SysDeptEntity> queryAll(Map<String, Object> params) {
        Long currUserId = ShiroUtil.getUserId();//获取当前用户id
        if(currUserId!=Constant.SUPER_ADMIN){//如果不是管理员，就需要查询该用户数据视野可见的部门信息，而不是所有部门信息
            String deptDataIds = commonDeptDataService.getCurrUserDataDeptIdsStr();
            params.put("deptDataIds",(StringUtils.isNotBlank(deptDataIds))?deptDataIds:null);
        }

        return baseMapper.queryList(params);
    }

    @Override
    public List<Long> queryDeptByParentId(Long parentId) {
        return baseMapper.queryDeptByParentId(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        List<Long> deptIdList = Lists.newLinkedList();

        //获取一级部门id列表
        List<Long> subIdList = baseMapper.queryDeptByParentId(deptId);
        getDeptTreeList(subIdList,deptIdList);
        return deptIdList;
    }

    /**
     * 递归获取部门id的子部门信息
     * @param subIdList
     * @param deptIdList
     */
    private void getDeptTreeList(List<Long> subIdList,List<Long> deptIdList){
        List<Long> currDeptIdList;
        for(Long subDeptId:subIdList){
            currDeptIdList = baseMapper.queryDeptByParentId(subDeptId);
            if(null!=currDeptIdList && !currDeptIdList.isEmpty()){
                getDeptTreeList(currDeptIdList,deptIdList);
            }

            //递归到叶子节点了，加入到集合中
            deptIdList.add(subDeptId);
        }
    }


}
