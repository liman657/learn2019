package com.learn.netty_im.websocket;

import com.learn.netty_im.SpringUtil;
import com.learn.netty_im.domain.TChatMsg;
import com.learn.netty_im.enums.MsgActionEnum;
import com.learn.netty_im.service.IUserService;
import com.learn.netty_im.utils.JsonUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:心跳检测的handler，这个是继承ChannelInboundHandlerAdapter
 * 从而不需要实现channelRead0方法
 */
@Slf4j
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //判断evt是否是IdleStateEvent（用于触发用户事件，包含读空闲/写空闲/读写空闲）
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if(event.state() == IdleState.READER_IDLE){//如果是读空闲的事件，不需要任何处理
                log.info("进入读空闲......");
            }

            if(event.state() == IdleState.WRITER_IDLE){//写空闲事件，不需要任何处理
                log.info("进入写空闲......");
            }

            if(event.state() == IdleState.ALL_IDLE){//读写空闲事件，说明开启了飞行模式，需要删除客户端channel

                int beforeChannelSize = ChatHandler.userChannels.size();
                log.info("关闭相关channel之前，服务端所剩channel数：{}",beforeChannelSize);
                Channel channel = ctx.channel();
                //关闭无用的channel，以防资源浪费
                channel.close();
                int afterChannelSize = ChatHandler.userChannels.size();
                log.info("关闭相关channel之后，服务端所剩channel数：{}",afterChannelSize);
            }
        }
    }
}
