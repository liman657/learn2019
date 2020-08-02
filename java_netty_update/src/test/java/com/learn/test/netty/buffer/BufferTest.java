package com.learn.test.netty.buffer;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Slf4j
public class BufferTest {


    private static IntBuffer intBuffer;

    @Test
    public void testBufferCreate(){
        IntBuffer intBuffer = IntBuffer.allocate(20);
        logPrintBufferField(intBuffer);
    }

    @Test
    public void testBufferPut(){
        intBuffer = IntBuffer.allocate(20);
        for(int i = 0;i<5;i++){
            intBuffer.put(i);
        }

        log.info("------after put info ------");
        logPrintBufferField(intBuffer);
    }

    @Test
    public void testBufferFlip() {
        testBufferPut();
        intBuffer.flip();//由写模式转变成读模式
        log.info("-----after flip -----");
        logPrintBufferField(intBuffer);
    }

    @Test
    public void testBufferGet(){
        testBufferFlip();
        for(int i =0;i<2;i++){
            int index = intBuffer.get();
            log.info("read index:{}",index);
        }
        log.info("----- after read 2 element -----");
        logPrintBufferField(intBuffer);

        for(int i=0;i<3;i++){
            int index = intBuffer.get();
            log.info("read index:{}",index);
        }
        log.info("----- after continue read 3 element -----");
        logPrintBufferField(intBuffer);
    }

    @Test
    public void testRewindBuffer(){
        testBufferGet();
        intBuffer.rewind();
        logPrintBufferField(intBuffer);
    }

    @Test
    public void testBufferClean(){
        testRewindBuffer();
        log.info("------ after clear ------");
        intBuffer.clear();
        logPrintBufferField(intBuffer);
    }

    private void logPrintBufferField(Buffer buffer){
        log.info("position:{}", buffer.position());
        log.info("limit:{}", buffer.limit());
        log.info("capacity:{}",buffer.capacity());
    }

}
