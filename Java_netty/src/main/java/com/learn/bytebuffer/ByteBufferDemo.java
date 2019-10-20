package com.learn.bytebuffer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * autor:liman
 * createtime:2019/10/19
 * comment: ByteBuffer的使用实例
 */
public class ByteBufferDemo {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("E:/liman/learn/test.txt");

        FileChannel fileChannel = fileInputStream.getChannel();
//        CharBuffer charBuffer = CharBuffer.allocate(1024);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fileChannel.read(byteBuffer);

        System.out.println(readByteBufferInfo(byteBuffer));
    }

    public static String readByteBufferInfo(ByteBuffer byteBuffer){
        byteBuffer.flip();
        char[] datas = null;
        Charset utf8 = Charset.forName("UTF-8");
        CharBuffer charBuffer = utf8.decode(byteBuffer);
        if(byteBuffer.remaining()>=0){
            datas = Arrays.copyOf(charBuffer.array(),charBuffer.limit());
        }
        String info = String.valueOf(datas);
        return info;
    }


}
