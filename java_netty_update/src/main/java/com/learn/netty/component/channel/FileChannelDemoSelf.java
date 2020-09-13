package com.learn.netty.component.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * autor:liman
 * createtime:2020/8/26
 * comment:FileChannel的简单实例
 */
@Slf4j
public class FileChannelDemoSelf {

    public static void main(String[] args) {
//        simpleCopyFile();
        simpleAppendInfo2File();
    }

    /**
     * 简单的文件拷贝
     */
    public static void simpleCopyFile(){
        String fileName = "F:\\git_learn\\index_newName.html";
        String copyFileName = "F:\\git_learn\\index_newName_bak.html";
        FileInputStream fileInputStream=null;
        FileChannel fileChannel = null;

        FileOutputStream fileOutputStream = null;
        FileChannel fileOutChannel = null;
        try {
            //1.构建文件输入流
            fileInputStream = new FileInputStream(new File(fileName));
            //2.获取文件channel
            fileChannel = fileInputStream.getChannel();

            fileOutputStream = new FileOutputStream(new File(copyFileName));
            fileOutChannel = fileOutputStream.getChannel();

            //3.实例化Buffer缓冲区
            ByteBuffer readBuffer = ByteBuffer.allocate(2048);//读缓冲区
            ByteBuffer writeBuffer = ByteBuffer.allocate(2048);//写缓冲区

            //4.将文件channel中的数据写入到buffer中。
            int readLength=-1;
            while((readLength=fileChannel.read(readBuffer))!=-1){
                int outLength = -1;
                readBuffer.flip();
                while((outLength=fileOutChannel.write(readBuffer))!=0){
                    System.out.println("写入的文件字节数:"+outLength);
                }
                readBuffer.clear();
            }
            fileOutChannel.force(true);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将简单的内容附加到文件末尾
     */
    public static void simpleAppendInfo2File(){
        String targetFileName = "F:\\git_learn\\index_newName_bak.html";
        FileInputStream fileInputStream=null;
        FileChannel fileChannel = null;
        try{
            //1.构建文件输入流
//            fileInputStream = new FileInputStream(new File(targetFileName));
//            //2.获取文件channel
//            fileChannel = fileInputStream.getChannel();//这种方式得到的文件channel只是可读，不是可读写的

            RandomAccessFile randomAccessFile = new RandomAccessFile(targetFileName,"rw");
            fileChannel = randomAccessFile.getChannel();

            //3.实例化Buffer缓冲区
            ByteBuffer readBuffer = ByteBuffer.allocate(2048);//读缓冲区
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);//写缓冲区

//            int readLength = fileChannel.read(readBuffer);//将文件中的内容读取到readBuffer中
            while(fileChannel.read(readBuffer)!=-1){
                readBuffer.flip();//readBuffer变成可读模式
                //读取buffer中的内容
                while(readBuffer.position()<readBuffer.limit()){
                    System.out.print((char)readBuffer.get());
                }
                readBuffer.clear();
//                readLength = fileChannel.read(readBuffer);
            }

            readBuffer.put("\nthis is append coment".getBytes("UTF-8"));
            readBuffer.flip();
            fileChannel.write(readBuffer);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
