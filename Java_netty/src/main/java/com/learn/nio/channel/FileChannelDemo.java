package com.learn.nio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * autor:liman
 * createtime:2019/10/7
 * comment:
 */
@Slf4j
public class FileChannelDemo {

    public static void main(String[] args) {
        //向文件中写入数据
        try{
            //创建文件，如果没有文件就创建文件
            File file = new File("E:/liman/learn/FileChannelTest.data");
            if(!file.exists()){
                file.createNewFile();
            }

            //根据文件输出流创建与这个文件相关的channel
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel fileChannel = fileOutputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(64);
            buffer.put("hello world file channel，就是这么牛逼。".getBytes("UTF-8"));
            buffer.flip();

            //利用write方法，将buffer中的数据写入到channel中
            fileChannel.write(buffer);
            buffer.clear();

            fileOutputStream.close();
            fileChannel.close();
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }

        //从FileChannel中读取数据
        try{
            Path path = Paths.get("E:/liman/learn/FileChannelTest.data");
            FileChannel fileChannel = FileChannel.open(path);

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) (fileChannel.size()+1));

            Charset utf8 = Charset.forName("UTF-8");

            fileChannel.read(byteBuffer);

            byteBuffer.flip();
            CharBuffer charBuffer = utf8.decode(byteBuffer);
            log.info("文件中的内容：{}",charBuffer.toString());
            byteBuffer.clear();
            fileChannel.close();
        }catch (Exception e){

        }
    }

}
