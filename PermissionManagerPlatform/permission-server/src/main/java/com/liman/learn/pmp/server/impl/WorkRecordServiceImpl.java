package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.AttendRecordEntity;
import com.liman.learn.pmp.model.mapper.AttendRecordDao;
import com.liman.learn.pmp.server.IWorkRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:
 */
@Service("workRecordService")
@Slf4j
public class WorkRecordServiceImpl extends ServiceImpl<AttendRecordDao,AttendRecordEntity> implements IWorkRecordService {

    @Autowired
    private Environment environment;

    /**
     * 考勤记录的分页查询，写法和之前不同
     * @param params
     * @return
     */
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        //构造pageNo pageSize
        IPage<AttendRecordEntity> queryPageResult = new QueryUtil<AttendRecordEntity>().getQueryPage(params);
        List<AttendRecordEntity> attendRecordPageList = baseMapper.queryPage(queryPageResult, params);
        queryPageResult.setRecords(attendRecordPageList);
        return new PageUtil(queryPageResult);
    }

    /**
     * 查询所有记录
     * @param params
     * @return
     */
    @Override
    public List<AttendRecordEntity> selectAllWorkRecordList(Map<String, Object> params) {
        return baseMapper.selectExportData(params);
    }

    /**
     * 中间站转换处理为导出数据服务
     * @param workRecordList
     * @return
     */
    @Override
    public List<Map<Integer, Object>> manageExport(List<AttendRecordEntity> workRecordList) {
        List<Map<Integer, Object>> listMap= Lists.newLinkedList();

        //"ID","部门名称","姓名","日期","打卡状态","打卡开始时间","打卡结束时间","工时/小时"
        if (workRecordList!=null && !workRecordList.isEmpty()){
            workRecordList.stream().forEach(entity -> {
                try {
                    Map<Integer,Object> rowMap=Maps.newHashMap();

                    rowMap.put(0,entity.getId());
                    rowMap.put(1,entity.getDeptName());
                    rowMap.put(2,entity.getUserName());
                    rowMap.put(3, Constant.DATE_FORMAT.format(entity.getCreateTime()));
                    Constant.AttendStatus status= Constant.AttendStatus.byCode(entity.getStatus().intValue());
                    rowMap.put(4,status!=null?status.getMsg():"空");
                    rowMap.put(5, Constant.DATE_TIME_FORMAT.format(entity.getStartTime()));
                    rowMap.put(6, Constant.DATE_TIME_FORMAT.format(entity.getEndTime()));
                    rowMap.put(7,entity.getTotal());

                    listMap.add(rowMap);
                }catch (Exception e){}
            });
        }

        return listMap;
    }
}
