package com.learn.netty_im.websocket;

import com.learn.netty_im.domain.TChatMsg;
import com.learn.netty_im.enums.MsgActionEnum;
import com.learn.netty_im.service.IUserService;
import com.learn.netty_im.utils.JsonUtils;
import com.learn.netty_im.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:聊天的后端处理
 * 难点：handler中无法直接注入spring的service类，需要通过SpringUtil 来获取指定的业务类
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //用于记录和管理所有客户端的channel
    private static ChannelGroup userChannels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 连接出现异常的时候，会触发这个方法，这里也要删除用户的channel
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常的堆栈信息
        cause.printStackTrace();
        //发生异常之后，关闭channel，随后从channelGroup中移出用户对应的channel
        ctx.channel().close();
        userChannels.remove(ctx.channel());
//        super.exceptionCaught(ctx, cause);
    }

    @Override
    public boolean acceptInboundMessage(Object msg) throws Exception {
        return super.acceptInboundMessage(msg);
    }

    /**
     * 读取客户端消息的方法
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
//        String content = msg.text();
//        log.info("接收到客户端的消息为:{}",content);
//        userChannels.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now()
//                + "接受到客户端的消息消息, 消息为：" + content));
        Channel currentChannel = ctx.channel();
        //1.获得客户端发送过来的消息
        String content = msg.text();
        MsgContent msgContent = JsonUtils.jsonToPojo(content, MsgContent.class);
        log.info("收到客户端发来的消息为:{}",msgContent);
        Integer msgAction = msgContent.getAction();
        //2.判断消息的类型，这里需要通过一个枚举来进行定义，根据不同的类型来处理不同的业务
        if (msgAction == MsgActionEnum.CONNECT.type) {
            // 2.1 websocket第一次open的时候，初始化channel的时候，把用户的channel和用户id进行关联
            String sendUserId = msgContent.getChatMsg().getSendUserId();
            UserChannelRel.putUserChannel(sendUserId, currentChannel);//直接放到我们定义的map中即可。
            // 测试
//            for (Channel c : userChannels) {
//                System.out.println(c.id().asLongText());
//            }
//            UserChannelRel.output();
        } else if (msgAction == MsgActionEnum.CHAT.type) {
            //  2.2  聊天类型的消息，把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
            ChatMsg chatMsg = msgContent.getChatMsg();
            String sendUserId = chatMsg.getSendUserId();
            String toUserId = chatMsg.getAcceptUserId();
            String msgText = chatMsg.getMsg();

            //保存消息对象到数据库，并且标记为未签收
            IUserService userService = (IUserService) SpringUtil.getBean("userService");
            TChatMsg tChatMsgEntity = new TChatMsg();
            tChatMsgEntity.setAcceptUserId(toUserId);
            tChatMsgEntity.setSendUserId(sendUserId);
            tChatMsgEntity.setMsg(msgText);
            String chatMsgId = userService.saveMsg(tChatMsgEntity);
            //设置chatMsg的消息id
            chatMsg.setMsgId(chatMsgId);

            //发送消息到接收方的channel
            Channel receiveChannel = UserChannelRel.getUserChannel(toUserId);
            if (null == receiveChannel) {
                //如果不存在，表示接受方未连接到服务器。利用第三方的组件进行消息推送（JPush,个推，小米推送等）

            } else {
                //如果不为空，则表示channel存在，需要在channelGroup中去查找对应的channel。
                Channel findChannel = userChannels.find(receiveChannel.id());
                if (null != findChannel) {//这里表示用户在线
                    receiveChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(chatMsg)));
                } else {
                    //用户离线，需要推送
                }
            }
        } else if (msgAction == MsgActionEnum.SIGNED.type) {//接收方的手机接收到消息，就需要做签收处理
            //  2.3  签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
            IUserService userService = (IUserService) SpringUtil.getBean("userService");
            //通过MsgContent对象中的扩展字段来存储消息id，由逗号分隔
            String msgIdStr = msgContent.getExtand();
            String[] msgIds = msgIdStr.split(",");
            List<String> msgIdList = new ArrayList<>();
            for(String str:msgIds){
                if(StringUtils.isNotBlank(str)){
                    msgIdList.add(str);
                }
            }

            log.info("准备签收的消息列表为:{}",msgIdList);
            if(!CollectionUtils.isNotEmpty(msgIdList)){
                //开始批量签收
                userService.udpateMsgSigned(msgIdList);
            }


        } else if (msgAction == MsgActionEnum.KEEPALIVE.type) {
            //  2.4  心跳类型的消息

        }
        // 2.1 websocket第一次open的时候，初始化channel的时候，把用户的channel和用户id进行关联
        // 2.2 聊天类型的文本消息，把聊天记录保存到数据库，同时标记聊天消息的签收状态，将其改为[未签收]
        // 2.3 签收消息，针对具体的消息进行签收，其实就是修改数据库中对应消息的签收状态，将其改为[已签收]
        // 2.4 心跳类型的消息。

    }

    /**
     * 客户端与服务端连接成功之后触发方法
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        userChannels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        log.info("客户端断开，channel对应的长id为:{}", ctx.channel().id().asLongText());
        log.info("客户端断开，channel对应的短id为:{}", ctx.channel().id().asLongText());
        //当触发通道移出的时候，调用channel中的remove方法。
        userChannels.remove(ctx.channel());

    }


}
