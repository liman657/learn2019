//package com.learn.netty.reactor;
//
//import java.io.IOException;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.util.Iterator;
//import java.util.Set;
//
///**
// * autor:liman
// * createtime:2020/9/9
// * comment:
// */
//public class TimeServerAcceptor {
//
//    private Selector selector;
//
//    private ServerSocketChannel serverSocketChannel;
//
//    public TimeServerAcceptor(String host, int port){
//        if(null == selector){
//            try {
//                selector = Selector.open();
//                serverSocketChannel = ServerSocketChannel.open();
//                serverSocketChannel.configureBlocking(false);
//                SelectionKey acceptSelectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//                acceptSelectionKey.attach(new AcceptorHandler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public void acceptHandler(){
//        while(true){
//            try {
//                selector.select(1000);
//                Set<SelectionKey> selectionKeys = selector.selectedKeys();
//                Iterator<SelectionKey> it = selectionKeys.iterator();
//                while(it.hasNext()){
//                    SelectionKey key = it.next();
//                    key.attachment();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
