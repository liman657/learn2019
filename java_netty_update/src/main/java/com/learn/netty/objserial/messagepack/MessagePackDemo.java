package com.learn.netty.objserial.messagepack;

import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:MessagePack解压缩的简单实例
 */
@Slf4j
public class MessagePackDemo {

    public static void main(String[] args) throws IOException {
        List<String> components = new ArrayList<String>();
        components.add("msgpack");
        components.add("kumofs");
        components.add("vivers");

        //序列化components
        MessagePack messagePack = new MessagePack();
        byte[] afterSeialBytes = messagePack.write(components);
        log.info("序列化之后bytes的长度为:{}",afterSeialBytes.length);

        //JDK序列化指定对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(components);
        os.flush();
        os.close();
        byte[] jdkByte = bos.toByteArray();
        log.info("JDK序列化之后的数组长度为：{}",jdkByte.length);

        List<String> readList = messagePack.read(afterSeialBytes, Templates.tList(Templates.TString));
        log.info("序列化之后的list内容为:");
        readList.stream().forEach(t->log.info("{}",t));
    }
}