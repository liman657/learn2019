package com.learn.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * autor:liman
 * createtime:2020/9/19
 * comment:三种ByteBuf的种类介绍
 */
@Slf4j
public class ByteBufTypeDemo {

    private static final Charset UTF8_CharSet = Charset.forName("UTF-8");

    public static void main(String[] args) {
        testHeapByteBuf();
        testDirectByteBuf();
        testCompositeBuffer();
    }

    /**
     * 测试Heaper Buffer
     */
    public static void testHeapByteBuf() {
        ByteBuf heapBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("Heap Buf的简单实例".getBytes(UTF8_CharSet));
        if (heapBuf.hasArray()) {
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            String s = new String(array, offset, length, UTF8_CharSet);
            log.info("Heap Buf 读取结果为:{}", s);
        }
        heapBuf.release();
    }

    /**
     * 测试的directBuffer
     */
    public static void testDirectByteBuf() {
        ByteBuf directBuf = ByteBufAllocator.DEFAULT.directBuffer();
        directBuf.writeBytes("direct Buf的简单实例".getBytes(UTF8_CharSet));
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            directBuf.getBytes(directBuf.readerIndex(), array);
            String content = new String(array, UTF8_CharSet);
            log.info("direct buf 读取的数据为:{}", content);
        }
        directBuf.release();
    }

    /**
     * 测试组合缓冲区
     */
    public static void testCompositeBuffer() {
        CompositeByteBuf compositeByteBuf = ByteBufAllocator.DEFAULT.compositeBuffer();
        //这个底层会调用并分配一个堆缓冲区
        ByteBuf headerBuf = Unpooled.copiedBuffer("组合缓冲区的header内容", UTF8_CharSet);
        ByteBuf bodyBuf = Unpooled.copiedBuffer("组合缓冲区的body部分", UTF8_CharSet);
        compositeByteBuf.addComponents(headerBuf, bodyBuf);
        readCompositeBufferInfo(compositeByteBuf);
    }

    /**
     * 读取compositeBuffer中的内容
     *
     * @param compositeByteBuf
     */
    public static void readCompositeBufferInfo(CompositeByteBuf compositeByteBuf) {
        String result = "";
        for (ByteBuf byteBuf : compositeByteBuf) {
            int length = byteBuf.readableBytes();
            byte[] array = new byte[length];
            byteBuf.getBytes(byteBuf.readerIndex(), array);
            result = new String(array, UTF8_CharSet);
            log.info("composite buffer读取结果：{}", result);
        }
    }
}