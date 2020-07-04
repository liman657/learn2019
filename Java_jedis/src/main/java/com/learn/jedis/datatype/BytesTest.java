package com.learn.jedis.datatype;

import com.learn.jedis.util.JedisUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * autor:liman
 * createtime:2020/7/4
 * comment:Byte数据类型
 */
public class BytesTest {
    public static void main(String[] args) throws IOException {
        System.out.println(Charset.defaultCharset());
        File file = new File("D:/1.png");
        byte[] bytes = FileUtils.readFileToByteArray(file);

        String str = new String(bytes);
        System.out.println(str);

        JedisUtil.getJedisUtil().set("mybytes", str);
    }
}
