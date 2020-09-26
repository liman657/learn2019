package com.learn.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/19
 * comment:基本的ByteBuf读写
 */
@Slf4j
public class BaseReadAndWriteBuf {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9,100);
        log.info("分配初始化9，最大空间100的buffer，{}",buffer);
        byte[] data = new byte[]{1,2,3,4};
        log.info("开始写入数据");
        writeData(buffer,data);
        log.info("读数据");
        readDataFromBuffer(buffer);
        log.info("取数据");
        getByteFromBuffer(buffer);
        log.info("all done!");
    }

    public static void writeData(ByteBuf buffer,byte[] dataByte){
        buffer.writeBytes(dataByte);
    }

    public static void readDataFromBuffer(ByteBuf buffer){
        while(buffer.isReadable()){
            log.info("读一个字节:{}",buffer.readByte());
        }
    }

    public static void getByteFromBuffer(ByteBuf buffer){
        for(int i=0;i<buffer.readableBytes();i++){
            log.info("获取一个字节：{}",buffer.getByte(i));
        }
    }
}