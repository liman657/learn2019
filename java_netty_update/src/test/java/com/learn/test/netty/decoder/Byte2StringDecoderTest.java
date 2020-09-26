package com.learn.test.netty.decoder;

import com.learn.netty.decodeAndEncode.IntValuePlus100Decoder;
import com.learn.netty.decodeAndEncode.Byte2CharStringHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * autor:liman
 * createtime:2020/9/25
 * comment:自定义解码器的测试
 */
@Slf4j
public class Byte2StringDecoderTest {


    @Test
    public void testByte2CharStringDecoder(){
        ChannelInitializer testChannel = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new IntValuePlus100Decoder());
                ch.pipeline().addLast(new Byte2CharStringHandler());
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(testChannel);
        for(int j = 0;j<100;j++){
            ByteBuf buf = Unpooled.buffer();
            buf.writeBytes("test".getBytes(CharsetUtil.UTF_8));
            embeddedChannel.writeInbound(buf);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
