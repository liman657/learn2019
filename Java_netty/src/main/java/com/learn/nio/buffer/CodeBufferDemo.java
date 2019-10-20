package com.learn.nio.buffer;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * autor:liman
 * createtime:2019/10/7
 * comment:对buffer进行编码和解码的实例
 */
@Slf4j
public class CodeBufferDemo {

    /**
     * 对buffer进行解码
     * @param str
     * @throws UnsupportedEncodingException
     */
    public static void decode(String str) throws UnsupportedEncodingException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes("UTF-8"));
        byteBuffer.flip();

        Charset utf8 = Charset.forName("UTF-8");
        //对ByteBuffer中的内容进行解码，将解码后的内容放到CharBuffer中
        CharBuffer charBuffer = utf8.decode(byteBuffer);

        char[] charArr = Arrays.copyOf(charBuffer.array(),charBuffer.limit());
        System.out.println(charArr);
    }

    public static void encode(String str){
        CharBuffer charBuffer = CharBuffer.allocate(128);
        charBuffer.append(str);
        charBuffer.flip();

        Charset utf8 = Charset.forName("UTF-8");
        ByteBuffer byteBuffer = utf8.encode(charBuffer);//对charBuffer中的内容进行编码，然后放入到ByteBuffer中

        byte[] datas = Arrays.copyOf(byteBuffer.array(),byteBuffer.limit());
        System.out.println(datas);
        log.info(String.valueOf(datas));
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        encode("这是中文");
        decode("这是中文");
    }

}
