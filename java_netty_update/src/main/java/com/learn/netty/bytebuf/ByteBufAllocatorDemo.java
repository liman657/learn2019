package com.learn.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.UnpooledByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/19
 * comment:ByteBuf的allocator的简单实例
 */
@Slf4j
public class ByteBufAllocatorDemo {

    public static void main(String[] args) {
        ByteBuf buffer = null;
        buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        buffer = UnpooledByteBufAllocator.DEFAULT.buffer();//利用非池化分配方式，分配ByteBuf
        buffer = PooledByteBufAllocator.DEFAULT.directBuffer();//利用池化分配方式，分配堆外ByteBuf
        buffer = PooledByteBufAllocator.DEFAULT.heapBuffer();//利用池化分配方式，分配堆ByteBuf
    }

}
