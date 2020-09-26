package com.learn.netty.objserial.protobuf;

import com.learn.netty.objserial.UserEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:序列化客户端代码
 */
@Slf4j
public class ProtobufSerialClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserEntityRequestProto.UserEntityRequest.Builder builder = UserEntityRequestProto.UserEntityRequest.newBuilder();
        for (int i = 0; i < 10; i++) {
            if (i == 7) {
                builder.setSubReqID(1);
                builder.setType("seven");
                builder.setPassword("seven");
                builder.setUserName("seven");
            } else {
                builder.setSubReqID(1);
                builder.setType("request entity");
                builder.setPassword("request password");
                builder.setUserName("request test");
            }
            UserEntityRequestProto.UserEntityRequest requestEntity = builder.build();
            ctx.write(requestEntity);
        }
        ctx.flush();
//        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive server info :\n {}", msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public UserEntity createUserEntity(int id, String userName) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setUserid(id);
        return userEntity;
    }
}
