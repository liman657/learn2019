package com.learn.netty.objserial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:
 */
@Slf4j
public class ObjSerialServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserEntity requestUserEntity = (UserEntity) msg;
        log.info("server accept client info : {}",requestUserEntity.toString());
        UserEntity responseEntity = new UserEntity();
        if("testName".equalsIgnoreCase(requestUserEntity.getUserName())){
            responseEntity.setUserid(99);
            responseEntity.setUserName("responseEntity");
        }else{
            responseEntity.setUserName("simple response");
            responseEntity.setUserid(88);
        }
        ctx.writeAndFlush(responseEntity);
        super.channelRead(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        log.error("连接出现异常，异常信息为:{}",cause);
//        super.exceptionCaught(ctx, cause);
    }

    private UserEntity responseUserEntity(int userId, String userName){
        UserEntity respEntity = new UserEntity();
        respEntity.setUserid(userId);
        respEntity.setUserName(userName);
        return respEntity;
    }
}
