package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.SysLogEntity;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/12
 * comment:
 */
public interface ILogService extends IService<SysLogEntity> {

    /**
     * 日志记录的分页查询
     * @param params
     * @return
     */
    PageUtil queryPage(Map<String, Object> params);

    void truncate();

}
