package com.learn.nioself;

import com.learn.utils.IOUtil;
import jdk.management.resource.internal.inst.FileChannelImplRMHooks;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * autor:liman
 * createtime:2019/10/20
 * comment:
 */
@Slf4j
public class FileNIOCopyDemo {

    public static void main(String[] args) {
        nioCopySourceFile();
    }

    /**
     * 复制两个资源目录下的文件
     */
    private static void nioCopySourceFile() {

        String sourcePath = NioDemoConfig.FILE_RESOURCE_SRC_PATH;
        String srcPath = IOUtil.getResourcePath(sourcePath);
        log.info("srcPath = "+srcPath);

        String destPath = NioDemoConfig.FILE_RESOURCE_DEST_PATH;
        String destDecodePath = IOUtil.builderResourcePath(destPath);
        log.info("destCodePath="+destDecodePath);

        nioCopyFile(srcPath,destDecodePath);
    }

    /**
     * 利用NIO的方式复制文件
     * @param srcPath
     * @param destDecodePath
     */
    private static void nioCopyFile(String srcPath, String destDecodePath) {

        File srcFile=new File(srcPath);
        File destFile = new File(destDecodePath);
        try{
           if(!destFile.exists()){
               destFile.createNewFile();
           }
        }catch (Exception e){
            e.printStackTrace();
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel =null;
        try{
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            int length = -1;
            ByteBuffer buf = ByteBuffer.allocate(1024);
            while((length=inChannel.read(buf))!=-1){
                buf.flip();//切到读模式
                int outLength = 0;
                //将buf写入到输出的通道
                while((outLength=outChannel.write(buf))!=0){
                    log.info("写入的字节数："+outLength);
                }
                buf.clear();
            }
            outChannel.force(true);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            IOUtil.closeQuietly(outChannel);
            IOUtil.closeQuietly(fos);
            IOUtil.closeQuietly(inChannel);
            IOUtil.closeQuietly(fis);
        }
    }
}