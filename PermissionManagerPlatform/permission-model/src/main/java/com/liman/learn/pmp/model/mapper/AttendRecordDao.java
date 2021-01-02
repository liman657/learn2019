package com.liman.learn.pmp.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.liman.learn.pmp.model.entity.AttendRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/23.
 */
@Mapper
public interface AttendRecordDao extends BaseMapper<AttendRecordEntity>{

    List<AttendRecordEntity> queryPage(IPage<AttendRecordEntity> page, @Param("paramMap") Map<String, Object> paramMap);

    List<AttendRecordEntity> selectExportData(Map<String, Object> params);

    List<AttendRecordEntity> queryPageSqlServer(Map<String, Object> params);


    //List<AttendRecordEntity> selectAllMysql(Map<String,Object> params);
}
























