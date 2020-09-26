package com.learn.netty.objserial;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:序列化客户端代码
 */
@Slf4j
public class ObjSerialClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            UserEntity userEntity = new UserEntity();
            if(i==7){
                userEntity.setUserName("testName");
                userEntity.setUserid(i);
            }else{
                userEntity = createUserEntity(i,"userName+"+i);
            }
            ctx.write(userEntity);
        }
        ctx.flush();
//        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive server info : {}", msg);
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
