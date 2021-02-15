package com.liman.learn.pmp.server;

import java.util.Set;

/**
 * autor:liman
 * createtime:2021/2/8
 * comment:部门数据视野服务类
 */
public interface ICommonDeptDataService {

    /**
     * 根据当前用户id，获取用户子部门信息
     * @return
     */
    public Set<Long> getCurrentUserDataDeptIds();

    /**
     * 返回的是string类型的
     * @return
     */
    public String getCurrUserDataDeptIdsStr();


}
