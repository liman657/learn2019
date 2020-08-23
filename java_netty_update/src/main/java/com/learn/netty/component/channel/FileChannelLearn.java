package com.learn.netty.component.channel;

import com.learn.netty.config.NioLearnConfig;
import com.learn.netty.util.IOUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@Slf4j
public class FileChannelLearn {

    public static void main(String[] args) {
        nioCopyResourceFile();
    }

    public static void nioCopyResourceFile(){
        String sourcePath = NioLearnConfig.FILE_RESOURCE_SRC_PATH;
        String srcPath = IOUtil.getResourcePath(sourcePath);
        log.info("srcPath={}",srcPath);

        String destPath = NioLearnConfig.FILE_RESOURCE_DEST_PATH;
        String destDecodePath = IOUtil.builderResourcePath(destPath);
        log.info("destDecodePath={}",destDecodePath);
        nioCopyFile(srcPath,destDecodePath);

    }

    public static void nioCopyFile(String srcPath,String destPath){
        File srcFile = new File(srcPath);
        File destFile =  new File(destPath);

        try{
            if(!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;
            try{
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outChannel = fos.getChannel();
                int length = -1;
                ByteBuffer buf = ByteBuffer.allocate(1024);
                //从输入通道读取到buf，buf默认是写入模式
                while((length = inChannel.read(buf))!=-1){
                    //将buffer切换到读模式
                    buf.flip();
                    int outLength = 0;

                    while((outLength = outChannel.write(buf))!=0){
                        log.info("写入字节数：{}",outLength);
                    }
                    //清除buffer，切换写入模式
                    buf.clear();
                }
                //强制将数据刷新到磁盘
                outChannel.force(true);
            }catch (Exception e){

            } finally {
              IOUtil.closeQuietly(outChannel);
              IOUtil.closeQuietly(fos);
              IOUtil.closeQuietly(inChannel);
              IOUtil.closeQuietly(fis);
            }
            long endTime = System.currentTimeMillis();
            log.info("文件复制耗时：{}",(endTime-startTime));
        }catch (Exception e){
            log.info("error info : {}",e);
        }
    }

}
