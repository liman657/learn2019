package com.liman.learn.pmp.controller.job;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.ValidatorUtil;
import com.liman.learn.pmp.controller.BaseController;
import com.liman.learn.pmp.model.entity.SysPostEntity;
import com.liman.learn.pmp.server.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/5
 * comment:岗位controller
 */
@RequestMapping("/post")
@Slf4j
@RestController
public class PostController extends BaseController {

    @Autowired
    private IPostService postService;

    @RequestMapping(value = "list")
    public BaseResponse listPostInfo(@RequestParam Map<String, Object> paramMap) {
        log.info("开始查询岗位信息,参数为:{}", paramMap);
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> resMap = Maps.newHashMap();
        try {
            PageUtil page = postService.queryPage(paramMap);
            resMap.put("page", page);

        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        log.info("岗位查询结果为:{}", resMap);
        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse savePostInfo(@RequestBody @Validated SysPostEntity entity, BindingResult result) {
        log.info("开始新增岗位,参数为:{}", entity);
        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)) {
            return new BaseResponse(StatusCode.InvalidParams.getCode(), res);
        }

        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            postService.savePost(entity);

        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        log.info("新增岗位结果为:{}", response);
        return response;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse delete(@RequestBody Long[] ids) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除岗位~接收到的数据：{}", Arrays.asList(ids));
            postService.deletePatch(ids);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }

        return response;
    }

    @RequestMapping(value = "/info/{id}")
    public BaseResponse getInfo(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> resMap = Maps.newHashMap();
        try {
            log.info("岗位详情~接收到数据：{}", id);

            resMap.put("post", postService.getById(id));

        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse updatePostInfo(@RequestBody @Validated SysPostEntity entity, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        if (entity.getPostId()==null || entity.getPostId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("修改岗位~接收到数据：{}",entity);

            postService.updatePost(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }

        return response;
    }


}





