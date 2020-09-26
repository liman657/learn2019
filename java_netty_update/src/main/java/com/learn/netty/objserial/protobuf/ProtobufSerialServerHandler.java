package com.learn.netty.objserial.protobuf;

import com.learn.netty.objserial.UserEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:
 */
@Slf4j
public class ProtobufSerialServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        UserEntityRequestProto.UserEntityRequest requestEntity = (UserEntityRequestProto.UserEntityRequest) msg;
        log.info("server receice message :\n {}",requestEntity.toString());

        if("seven".equalsIgnoreCase(requestEntity.getUserName())) {
            UserEntityResponseProto.UserEntityResponse.Builder userResponseBuilder = UserEntityResponseProto.UserEntityResponse.newBuilder();
            userResponseBuilder.setSubReqID(1);
            userResponseBuilder.setUserName("response entity");
            userResponseBuilder.setPassword("response pwd");
            userResponseBuilder.setType("response entity");
            UserEntityResponseProto.UserEntityResponse responseEntity = userResponseBuilder.build();

            ctx.writeAndFlush(responseEntity);
        }else{
            ctx.writeAndFlush("ordinal message");
        }
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
