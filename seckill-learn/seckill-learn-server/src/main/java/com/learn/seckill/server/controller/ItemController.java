package com.learn.seckill.server.controller;

import com.learn.seckill.model.entity.ItemKill;
import com.learn.seckill.server.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/2/22
 * comment: 商品controller
 */
@RequestMapping("/product")
@Slf4j
@Controller
public class ItemController {

    @Autowired
    private IProductService productService;

    /**
     * 获取商品列表
     */
    @RequestMapping(value = {"list"},method = RequestMethod.GET)
    public String list(ModelMap modelMap){
        try {
            //获取待秒杀商品列表
            List<ItemKill> list=productService.getKillItems();
            modelMap.put("list",list);

            log.info("获取待秒杀商品列表-数据：{}",list);
        }catch (Exception e){
            log.error("获取待秒杀商品列表-发生异常：",e.fillInStackTrace());
            //如果发生异常，则重定向到异常页面
            return "redirect:/page/error";
        }
        return "list";
    }

    @RequestMapping(value="/detail/{id}",method = RequestMethod.GET)
    public String seckillItemDetail(@PathVariable Integer id,ModelMap modelMap){
        if (id==null || id<=0){
            return "redirect:/page/error";
        }
        try {
            ItemKill detail=productService.getKillDetail(id);
            modelMap.put("detail",detail);
        }catch (Exception e){
            log.error("获取待秒杀商品的详情-发生异常：id={}",id,e.fillInStackTrace());
            return "redirect:/base/error";
        }
        return "info";
    }


}
