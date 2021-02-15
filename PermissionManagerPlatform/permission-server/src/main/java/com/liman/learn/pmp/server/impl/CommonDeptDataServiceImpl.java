package com.liman.learn.pmp.server.impl;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.model.mapper.SysDeptDao;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.server.ICommonDeptDataService;
import com.liman.learn.pmp.server.IDeptService;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * autor:liman
 * createtime:2021/2/8
 * comment: 部门数据视野服务类
 */
@Service("commonDeptDataService")
@Slf4j
public class CommonDeptDataServiceImpl implements ICommonDeptDataService {

    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private IDeptService deptService;

    /**
     * 获取当前用户 数据视野内可见的部门列表
     * @return
     */
    public Set<Long> getCurrentUserDataDeptIds(){
        Set<Long> dataIds = Sets.newHashSet();
        SysUserEntity currUser=ShiroUtil.getUserEntity();
        //如果是管理员，则直接查询所有的部门id
        if (Constant.SUPER_ADMIN == currUser.getUserId()){
            dataIds=sysDeptDao.queryAllDeptIds();

        }else{
            //分配给用户的部门数据权限id列表 该用户的一级部门列表
            Set<Long> userDeptDataIds=sysUserDao.queryDeptIdsByUserId(currUser.getUserId());
            if (CollectionUtils.isNotEmpty(userDeptDataIds)){
                dataIds.addAll(userDeptDataIds);
            }

            //用户所在的部门及其子部门Id列表 ~ 递归实现
            dataIds.add(currUser.getDeptId());
            List<Long> subDeptIdList=deptService.getSubDeptIdList(currUser.getDeptId());
            dataIds.addAll(Sets.newHashSet(subDeptIdList));
        }
        return dataIds;
    }

    public String getCurrUserDataDeptIdsStr(){
        String result = null;
        Set<Long> dataSet = this.getCurrentUserDataDeptIds();
        if(null!=dataSet && !dataSet.isEmpty()){
            String join = Joiner.on(",").join(dataSet);
            log.info("{}",join);
            result=CommonUtil.concatStrToInt(Joiner.on(",").join(dataSet),",");
        }
        return result;
    }

}
