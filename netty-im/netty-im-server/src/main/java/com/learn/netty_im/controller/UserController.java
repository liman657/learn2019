package com.learn.netty_im.controller;

import com.learn.enums.StatusCode;
import com.learn.netty_im.domain.TUsers;
import com.learn.netty_im.dto.FriendRequestVO;
import com.learn.netty_im.enums.SearchFriendsStatusEnum;
import com.learn.netty_im.pojo.bo.FaceImgBO;
import com.learn.netty_im.pojo.requsetentity.UserRequest;
import com.learn.netty_im.pojo.vo.UsersVO;
import com.learn.netty_im.service.IUserService;
import com.learn.netty_im.utils.FastDFSClient;
import com.learn.netty_im.utils.FileUtils;
import com.learn.netty_im.utils.MD5Utils;
import com.learn.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * autor:liman
 * createtime:2020/9/28
 * comment:用户controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @RequestMapping(value="/registerAndLogin",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse registerAndLogin(@RequestBody UserRequest userRequest){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            if(StringUtils.isBlank(userRequest.getUsername()) ||
                    StringUtils.isBlank(userRequest.getPassword())){
                log.error("用户名密码不能为空：{}",userRequest);
                result = new BaseResponse(StatusCode.InvalidParams);
            }
            String username = userRequest.getUsername();
            String password = userRequest.getPassword();
            String md5Password = MD5Utils.getMD5Str(password);

            boolean isExist = userService.isUserExist(username);
            log.info("判断用户：{}，是否存在:{}",username,isExist);
            TUsers userResult = null;
            if(isExist){//如果存在，则直接登录
                userResult = userService.queryUserForLogin(username,md5Password);
                if(null == userResult){
                    result = new BaseResponse(StatusCode.LoginFail);
                    return result;
                }
            }else{//注册
                userRequest.setNickname(username);
                userRequest.setPassword(md5Password);
                userRequest.setFaceImage("tempFaceId");
                userRequest.setFaceImageBig("tempFaceBigId");
                userResult = userService.saveUser(userRequest);
            }
            UsersVO frontUserVO = new UsersVO();
            BeanUtils.copyProperties(frontUserVO,userResult);
            result.setData(frontUserVO);
            log.info("返回结果为：{}",result);
        }catch (Exception e){
            log.error("服务器异常，异常信息为:{}",e);
            return new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    /**
     * 头像上传
     * @param userFaceImgBase64
     * @return
     */
    @RequestMapping(value="/uploadFaceImg",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse uploadFaceImg(@RequestBody FaceImgBO userFaceImgBase64)  {
        log.info("开始上传图片，参数信息为:{}",userFaceImgBase64);
        BaseResponse result = new BaseResponse(StatusCode.Success);
        //获取前端传递过来的base64字符串，然后转换成文件，然后上传
        try{
            String imgBase64 = userFaceImgBase64.getFaceData();
            String userFacePath = "E://IMNetty/userFace/"+userFaceImgBase64.getUserId()+"_userface.png";
            FileUtils.base64ToFile(userFacePath,imgBase64);

            MultipartFile faceFile2Upload = FileUtils.fileToMultipart(userFacePath);//将得到的file转换成Multipart;

            String faceFinalUrl = fastDFSClient.uploadFace(faceFile2Upload);//将转换成MultipartFile的人脸文件上传到fastDFS
            log.info("上传到fastDFS的文件url:{}",faceFinalUrl);

            //组装缩略图的url
            String[] contractUrl = faceFinalUrl.split("\\.");
            String thump = "_80x80.";
            String thumpImgUrl = contractUrl[0]+thump+contractUrl[1];//缩略图的url

            //保存用户信息到数据库
            UserRequest user =  new UserRequest();
            user.setId(userFaceImgBase64.getUserId());
            user.setUsername(userFaceImgBase64.getUsername());
            user.setFaceImage(thumpImgUrl);
            user.setFaceImageBig(faceFinalUrl);

            TUsers tUsers = userService.updateUserInfo(user);
            result.setData(tUsers);
        }catch (Exception e){
            log.error("服务器异常，异常信息为:{}",e);
            return new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    /**
     * 修改用户昵称
     * @param userRequest
     * @return
     */
    @RequestMapping(value="/updateNickName",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateUserInfo(@RequestBody UserRequest userRequest){
        log.info("开始修改用户昵称，参数信息为:{}",userRequest);
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            //修改用户信息
            UserRequest user =  new UserRequest();
            user.setId(userRequest.getId());
            user.setNickname(userRequest.getNickname());
            TUsers tUsers = userService.updateUserInfo(user);
            result.setData(tUsers);
        }catch (Exception e){
            log.error("服务器异常，异常信息为:{}",e);
            return new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    /**
     * 根据账号做匹配查询，而不是模糊匹配
     * @return
     */
    @RequestMapping(value="/searchFriend",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse searchFriend(@RequestParam String myUserId,@RequestParam String friendUserName) throws InvocationTargetException, IllegalAccessException {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(myUserId)||StringUtils.isBlank(friendUserName)){
            result = new BaseResponse(StatusCode.Fail);
        }

        Integer checkResult = userService.checkAddFriend(myUserId, friendUserName);
        if(SearchFriendsStatusEnum.SUCCESS.status == checkResult){
            TUsers tUsers = userService.queryUserInfoByUserName(friendUserName);
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(usersVO,tUsers);
            result.setData(usersVO);
        }else{
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(checkResult);
            result.setData(errorMsg);
        }
        return result;
    }

    /**
     * 添加好友的请求，只需要返回一个200的状态就可以了
     * @return
     */
    @RequestMapping(value="/addFriendRequest",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse addFriend(@RequestParam String myUserId,@RequestParam String friendUserName) throws InvocationTargetException, IllegalAccessException {
        log.info("开始添加好友，参数为：{}，{}",myUserId,friendUserName);
        BaseResponse result = new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(myUserId)||StringUtils.isBlank(friendUserName)){
            result = new BaseResponse(StatusCode.Fail);
        }

        Integer checkResult = userService.checkAddFriend(myUserId, friendUserName);
        if(SearchFriendsStatusEnum.SUCCESS.status == checkResult){
            userService.sendFriendRequest(myUserId,friendUserName);
        }else{
            String errorMsg = SearchFriendsStatusEnum.getMsgByKey(checkResult);
            result.setData(errorMsg);
        }
        return result;
    }

    /**
     * 查询好友请求列表
     * @param userId
     * @return
     */
    @PostMapping("/queryFriendRequests")
    public BaseResponse queryFriendRequests(@RequestParam("userId") String userId) {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        // 0. 判断不能为空
        if (StringUtils.isBlank(userId)) {
            result = new BaseResponse(StatusCode.Fail);
        }

        //查询接受到的好友请求
        List<FriendRequestVO> friendRequestVOS = userService.queryFriendRequestList(userId);
        result.setData(friendRequestVOS);
        return result;
    }
}
