package com.learn.springboot.redis.service.controller;

import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.service.service.problem.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Slf4j
@RestController
@RequestMapping("problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value="get",method=RequestMethod.GET)
    public BaseResponse get(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(problemService.getRandomProblemEntity());
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="total",method=RequestMethod.GET)
    public BaseResponse getAllTitle(@RequestParam("size") int size){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(problemService.getRandomEntitys(size));
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
