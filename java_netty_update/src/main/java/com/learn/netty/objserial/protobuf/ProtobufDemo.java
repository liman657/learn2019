package com.learn.netty.objserial.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:protobuf 简单实例
 */
@Slf4j
public class ProtobufDemo {

    public static void main(String[] args) throws InvalidProtocolBufferException {

        ProtoUserEntityProto.ProtoUserEntity protoUserEntity = ProtoUserEntityProto.ProtoUserEntity
                .newBuilder()
                .setSubReqID(1)
                .setUserName("userEntity")
                .setPassword("userEntityPassword")
                .build();

        log.info("before protobuf encode : {}",protoUserEntity.toString());
        byte[] bytes = protoUserEntity.toByteArray();
        log.info("after protobuf encode length:{}",bytes.length);
        log.info("after protobuf decode : {}",ProtoUserEntityProto.ProtoUserEntity.parseFrom(bytes).toString());
    }
}