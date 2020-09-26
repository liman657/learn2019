package com.learn.netty.decodeAndEncode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:自定义解码器的测试
 */
@Slf4j
public class Byte2StringDecoderTest {

    public static void main(String[] args) {
        new Byte2StringDecoderTest().testAdd100Decoder();
    }

    public void testAdd100Decoder(){
        ChannelInitializer testChannel = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new IntValuePlus100Decoder());
                ch.pipeline().addLast(new Byte2CharStringHandler());
            }
        };

        //将我们定义的channelInit传递给EmbeddedChannel
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(testChannel);
        int paramValue = new Random().nextInt(39);
        log.info("客户端即将要发送的int数据为:{}",paramValue);
        ByteBuf buf = Unpooled.buffer();
        buf.writeInt(paramValue);
        embeddedChannel.writeInbound(buf);//将buf写入EmbeddedChannel

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
