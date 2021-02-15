package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.AttendRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:
 */
public interface IWorkRecordService extends IService<AttendRecordEntity> {

    PageUtil queryPage(Map<String,Object> params);

    List<AttendRecordEntity> selectAllWorkRecordList(Map<String,Object> params);

    List<Map<Integer,Object>> manageExport(List<AttendRecordEntity> workRecordList);

}
