package com.learn.netty.objserial.messagepack;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:客户端的处理器
 */
@Slf4j
public class MessagePackClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] userInfos = getUserArray(10);
//        for(UserInfo user: userInfos){
//            ctx.writeAndFlush(user);
//        }

        for (int i = 0; i < userInfos.length; i++) {
            ctx.writeAndFlush(userInfos[i]);
        }
//        ctx.flush();
//        ctx.flush();
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("receive server msg info : {}", msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("client read complete info");
        ctx.flush();
//        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }


    /**
     * 构建长度为userNum的User对象数组
     *
     * @param userNum
     * @return
     */
    private UserInfo[] getUserArray(int userNum) {
        UserInfo[] users = new UserInfo[userNum];
        UserInfo user = null;
        for (int i = 0; i < userNum; i++) {
            user = new UserInfo();
            user.setName("userName --->" + i);
            user.setAge(i);
            users[i] = user;
        }
        return users;
    }
}
