package com.learn.IMChat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * autor:liman
 * createtime:2019/11/4
 * comment:消息解码器
 */
@Slf4j
public class IMMessageDecoder extends ByteToMessageDecoder {

    //解析IM写一下请求内容的正则
    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        try{
            final int length = in.readableBytes();
            final byte[] array = new byte[length];
            String content = new String(array,in.readerIndex(),length);//将buffer中的内容读取到array中

            if(!((null==content)|| "".equals(content.trim()))){
                if(!IMEnum.isIMP(content)){
                    channelHandlerContext.channel().pipeline().remove(this);//如果不是IM消息，则移出
                    return;
                }
            }

            in.getBytes(in.readerIndex(),array,0,length);
            out.add(new MessagePack().read(array,IMMessage.class));//将读取到的字节流，解析成IMMessage对象.
        }catch (Exception e){
            channelHandlerContext.pipeline().remove(this);
        }
    }

    /**
     * 将字符串解析成自定义的协议
     * @param msg
     * @return
     */
    public IMMessage decode(String msg){
        if(null == msg||"".equals(msg.trim())){
            return null;
        }
        try{
            Matcher m = pattern.matcher(msg);
            String header = "";
            String content = "";
            if(m.matches()){
                header = m.group(1);
                content = m.group(3);
            }

            String[] headers = header.split("\\]\\[");
            long time = 0;
            try{
                time = Long.parseLong(headers[1]);//获得消息报文中的请求时间
            }catch (Exception e){

            }
            String nickName = headers[2];
            //昵称最多十个字
            nickName = nickName.length() < 10 ? nickName : nickName.substring(0, 9);

            if(msg.startsWith("[" + IMEnum.LOGIN.getName() + "]")){//如果是登录消息
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else if(msg.startsWith("[" + IMEnum.CHAT.getName() + "]")){//如果是交谈消息
                return new IMMessage(headers[0],time,nickName,content);
            }else if(msg.startsWith("[" + IMEnum.FLOWER.getName() + "]")){//如果是鲜花消息
                return new IMMessage(headers[0],headers[3],time,nickName);
            }else{
                return null;
            }
        }catch (Exception e){
            log.error("解析报文异常,{}",e.fillInStackTrace());
            return null;
        }
    }
}
