package com.learn.netty.component.buffer;

import lombok.extern.slf4j.Slf4j;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * autor:liman
 * createtime:2020/8/26
 * comment:NIO 的三个组件之一buffer的实例
 */
@Slf4j
public class BufferDemo {

    public static IntBuffer intBuffer = null;

    public static void main(String[] args) {
        allocateBuffer();
        writeBuffer(initBuffer(),6);
//        readBuffer(intBuffer);
//        readWind(intBuffer);
        markAndResetRead(intBuffer);
    }

    public static IntBuffer initBuffer(){
        intBuffer = IntBuffer.allocate(20);
        return intBuffer;
    }


    /**
     * buffer分配空间，buffer创建之后，默认进入写模式
     */
    public static void allocateBuffer(){
        //Buffer.allocate，分配指定空间的buffer
        intBuffer = IntBuffer.allocate(20);
        log.info("buffer allocate finished");
        printBufferPropertiesInfo(intBuffer);
    }

    /**
     * 往buffer中写入数据
     * @param buffer
     * @param count
     */
    public static void writeBuffer(IntBuffer buffer,int count){
        int i = 0;
        while(i<count){
            buffer.put(i+1);
            i++;
        }
        printBufferPropertiesInfo(buffer);
    }

    /**
     * 从buffer中读取，从写入模式切换到读取模式，需要调用buffer的flip方法
     * @param buffer
     */
    public static void readBuffer(IntBuffer buffer){
        buffer.flip();
        int index = 0;
        printBufferPropertiesInfo(buffer);
        while(index<buffer.limit()){
            log.info("read info {}",buffer.get());
            index++;
        }
        log.info("after read print buffer info");
        printBufferPropertiesInfo(buffer);
    }

    /**
     * 倒带读取
     * @param buffer
     */
    public static void readWind(IntBuffer buffer){
        log.info("after rewind");
        buffer.rewind();//倒带的操作
        printBufferPropertiesInfo(buffer);
        log.info("rewind read buffer info");
        int index = 0;
        while(index<buffer.limit()){
//            System.out.println();
            log.info("buffer item : {}",buffer.get(index++));
        }
//        buffer.flip();
//        readBuffer(buffer);
//        System.out.println(buffer.get());
    }

    /**
     * 测试mark和reset的操作
     * @param buffer
     */
    public static void markAndResetRead(IntBuffer buffer){
        buffer.flip();
        int i = 0;
        log.info("normal read");
        printBufferPropertiesInfo(buffer);
        while(i<buffer.limit()){
            if(i==2){
                log.info("mark index");
                buffer.mark();//这里标记的一个位置
            }
            log.info("mark read item:{}",buffer.get());
            i++;
        }
        printBufferPropertiesInfo(buffer);
        log.info("reset read");
        buffer.reset();//回退到上一次mark的位置
        printBufferPropertiesInfo(buffer);
        while(buffer.position()<buffer.limit()){
            log.info("after reset read item:{}",buffer.get());
        }

    }


    /**
     * 打印出buffer各个属性的位置
     * @param buffer
     */
    public static void printBufferPropertiesInfo(Buffer buffer){
        int position = buffer.position();
        int limit = buffer.limit();
        int capacity = buffer.capacity();
        log.info("================= print buffer info start =================");
        log.info("position:{}",position);
        log.info("limit:{}",limit);
        log.info("capacity:{}",capacity);
        log.info("================= print buffer info end =================");
    }
}