package com.learn.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * autor:liman
 * createtime:2020/9/20
 * comment:Http服务处理类
 */
@Slf4j
public class HttpBusHandler extends ChannelInboundHandlerAdapter {

    private String result = "";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        try{
            String path = httpRequest.uri();
            String body = httpRequest.content().toString(CharsetUtil.UTF_8);
            HttpMethod method = httpRequest.method();
            if(!"/selfHttp".equalsIgnoreCase(path)){
                result="非法请求:"+path;
                send(result,ctx,HttpResponseStatus.BAD_REQUEST);
            }

            //GET方法请求
            if(HttpMethod.GET.equals(method)){
                log.info("GET请求方法，body内容为:{}",body);
                result = "服务端GET方法接受请求，处理成功，这是GET返回结果";
                send(result,ctx,HttpResponseStatus.OK);
            }

            //POST方法请求
            if(HttpMethod.POST.equals(method)){
                log.info("POST 请求方法，body内容为:{}",body);
                result = "服务端POST方法接受请求，处理成功，这是POST返回结果";
                send(result,ctx,HttpResponseStatus.OK);
            }
//            super.channelRead(ctx, msg);
        }catch (Exception e){

        }finally {
            httpRequest.release();
        }

    }

    /**
     * 发送消息
     * @param content
     * @param ctx
     * @param status
     */
    private void send(String content, ChannelHandlerContext ctx, HttpResponseStatus status){
        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer(content,CharsetUtil.UTF_8));

        httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");


        ctx.writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
    }
}
