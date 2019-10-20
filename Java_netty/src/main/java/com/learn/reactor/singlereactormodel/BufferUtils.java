package com.learn.reactor.singlereactormodel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * autor:liman
 * createtime:2019/10/19
 * comment:
 */
public class BufferUtils {

    public static String readByteBufferInfo(ByteBuffer byteBuffer){
        byteBuffer.flip();
        char[] datas = null;
        Charset utf8 = Charset.forName("UTF-8");
        CharBuffer charBuffer = utf8.decode(byteBuffer);
        if(byteBuffer.remaining()>=0){
            datas = Arrays.copyOf(charBuffer.array(),charBuffer.remaining());
        }
//        byteBuffer.mark();
        String info = String.valueOf(datas);
        return info;
    }

    public static ByteBuffer writeByteBufferInfo(String info){
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.append(info);
        charBuffer.flip();
//        System.out.println(charBuffer.array());

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuffer writeBuffer = utf8.encode(charBuffer);//对charBuffer中的内容进行编码，然后放入到ByteBuffer中
//        System.out.println();

//        byte[] datas = Arrays.copyOf(writeBuffer.array(),writeBuffer.remaining());
//        System.out.println(String.valueOf(datas));
        System.out.println(readByteBufferInfo(writeBuffer));
        return writeBuffer;
    }
}
