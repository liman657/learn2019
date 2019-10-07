package com.learn.nio.buffer;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * autor:liman
 * createtime:2019/10/6
 * comment:
 */
public class ByteBufferDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("E:/liman/learn/test.txt");
        //创建文件的操作channel
        FileChannel channel = fileInputStream.getChannel();

        //分配一个长度为10的buffer
        ByteBuffer buffer = ByteBuffer.allocate(10);
        printBuffer("初始化",buffer);

        channel.read(buffer);

        //操作之前先调用flip，锁定读取buffer的范围
        buffer.flip();
        printBuffer("锁定读取数据的范围",buffer);

        //判断有没有可读数据
        System.out.println(buffer.remaining());
        while(buffer.remaining()>=0){
            byte b = buffer.get();
            System.out.print((char)b);
            channel.write(buffer);
        }
        printBuffer("获取buffer中的数据",buffer);

        buffer.clear();

        fileInputStream.close();
    }

    /**
     * 打印buffer的一些状态
     * @param step
     * @param buffer
     */
    private static void printBuffer(String step,ByteBuffer buffer){
        System.out.println(step+":");
        //容量，数组大小
        System.out.print("capacity: " + buffer.capacity() + ", ");
        //当前操作数据所在的位置，也可以叫做游标
        System.out.print("position: " + buffer.position() + ", ");
        //锁定值，flip，数据操作范围索引只能在position - limit 之间
        System.out.println("limit: " + buffer.limit());
        System.out.println();
    }

}
