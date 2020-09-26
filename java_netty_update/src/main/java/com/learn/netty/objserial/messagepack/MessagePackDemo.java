package com.learn.netty.objserial.messagepack;

import lombok.extern.slf4j.Slf4j;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
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
        MessagePack messagePack = new MessagePack();
        //序列化components
        byte[] afterSeialBytes = messagePack.write(components);
        log.info("序列化之后bytes的长度为:{}",afterSeialBytes.length);
        List<String> readList = messagePack.read(afterSeialBytes, Templates.tList(Templates.TString));
        log.info("序列化之后的list内容为:");
        readList.stream().forEach(t->log.info("{}",t));
    }

}
