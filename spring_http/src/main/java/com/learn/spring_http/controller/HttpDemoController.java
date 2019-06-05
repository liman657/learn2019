package com.learn.spring_http.controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * author:liman
 * createtime:2019/5/30
 * comment:http请求实例
 */
@RequestMapping("/HttpDemo")
@RestController
public class HttpDemoController {

    /**
     * headers 可以规定只处理有指定属性的请求。这里是只处理Header中有Referer=http://www.baidu.com的报文
     * @param ownerId
     * @param petId
     */
    @RequestMapping(value="/test/{ownerId}/{petId}",headers="Referer=http://www.baidu.com")
    public void testHeader(@PathVariable String ownerId,@PathVariable String petId){
        System.out.println(ownerId+":"+petId);
        System.out.println("test");
    }

    /**
     * headers 只是在请求头中增加报文键值对而已
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/test/contentType",headers = "Accept=application/json")
    public void testContentType(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String jsonData = "{\"username\":\"zhang\", \"password\":\"123\"}";
        response.getWriter().write(jsonData);
    }

    @RequestMapping(value="/test/param",headers = "params=test")
    public void testParams(HttpServletRequest request){
        String testValue = request.getHeader("params");
        System.out.println(testValue);
    }

    @RequestMapping(value="test/consumes",consumes = "application/json",produces = "application/json")
    @ResponseBody
    public String testConsumes(@RequestBody UserEntity userEntity){
        System.out.println(userEntity.toString());
        String jsonData = "{\"username\":\"zhang\", \"password\":\"123\"}";
        return jsonData;
    }

    @RequestMapping(value="test/httpClient")
    public String testHttpClient(){
        String jsonData = "{\"username\":\"zhang\", \"password\":\"123\"}";
        return jsonData;
    }

    @RequestMapping("/test/param")
    public String testHttpClientParam(String name,String age){
        return "获取到的参数:"+name+":"+age;
    }

    @RequestMapping("/test/post")
    public String testPostwithOutParam(){
        return "test";
    }

    @RequestMapping(value="/test/postParam",method=RequestMethod.POST)
    public String testPostParam(String name,Integer age){
        return "普通参数的post请求操作："+name+":"+age;
    }

    @RequestMapping(value="/test/userEntity",method = RequestMethod.POST)
    public String testModelParam(@RequestBody UserEntity userEntity){
        return userEntity.toString();
    }

    @RequestMapping(value="/test/userAndParam",method=RequestMethod.POST)
    public String testPostUserAndParam(@RequestBody UserEntity userEntity,Integer flag,String meaning){
        return userEntity.toString()+":"+flag+":"+meaning;
    }

}
