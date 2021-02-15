package com.liman.learn.pmp.controller.workrecord;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.common.PoiService;
import com.liman.learn.pmp.common.WebOperatorService;
import com.liman.learn.pmp.model.entity.AttendRecordEntity;
import com.liman.learn.pmp.server.IWorkRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:考勤模块controller
 */
@Slf4j
@RequestMapping("workrecord")
@RestController
public class WorkRecordController {

    @Autowired
    private IWorkRecordService workRecordService;
    @Autowired
    private PoiService poiService;
    @Autowired
    private WebOperatorService webOperatorService;

    //列表
    @ResponseBody
    @RequestMapping("/list")
    public BaseResponse list(@RequestParam Map<String, Object> params){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();

        try {
            log.info("---考勤列表---");
            PageUtil page = workRecordService.queryPage(params);
            resMap.put("page", page);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }


    //导出
    @RequestMapping("/export")
    public @ResponseBody String export(@RequestParam Map<String, Object> params, HttpServletResponse response){
        final String[] headers=new String[]{"ID","部门名称","姓名","日期","打卡状态","打卡开始时间","打卡结束时间","工时/小时"};
        try {
            String fileName=new StringBuilder("考勤明细-").append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())).toString();
            String excelName=fileName+".xls";

            //针对于具体的业务模块-查询相应的数据，并做 “行转列映射” 的处理
            List<AttendRecordEntity> list=workRecordService.selectAllWorkRecordList(params);
            List<Map<Integer, Object>> listMap=workRecordService.manageExport(list);

            //以下是通用的
            Workbook wb=poiService.fillExcelSheetData(listMap,headers,fileName);
            webOperatorService.downloadExcel(response,wb,excelName);

            return excelName;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
