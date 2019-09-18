package com.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * autor:liman
 * createtime:2019/9/5
 * comment:Watcher的实例
 */
public class CuratorWatcherDemo {

    private static String connectionStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        //zk中提供了三种监听机制
        //PathChildCache 针对子节点的创建，删除和更新触发时间
        //NodeCache 针对当前节点的变化触发事件
        //TreeCache 综合事件
        //建立连接
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectionStr).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        curatorFramework.start();//启动

//        addListenerWithNode(curatorFramework);
        addListenerWithChildNode(curatorFramework);
        System.in.read();
    }

    /**
     * NodeCache 针对当前节点的变化触发事件，如果当前节点的子节点有变化，这个并不会触发该事件
     * @param curatorFramework
     * @throws Exception
     */
    private static void addListenerWithNode(CuratorFramework curatorFramework) throws Exception {
        NodeCache nodeCache = new NodeCache(curatorFramework,"/learn/data");
        NodeCacheListener nodeCacheListener = ()->{
            System.out.println("receive node changed");
            //拿到节点变化之后的值。
            System.out.println(nodeCache.getCurrentData().getPath()+"/"+new String(nodeCache.getCurrentData().getData()));
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();
    }

    /**
     * NodeCache 针对当前节点的变化触发事件，如果当前节点的子节点有变化，这个并不会触发该事件
     * @param curatorFramework
     * @throws Exception
     */
    private static void addListenerWithChildNode(CuratorFramework curatorFramework) throws Exception {
        PathChildrenCache nodeChildCache = new PathChildrenCache(curatorFramework,"/learn",true);
        PathChildrenCacheListener nodeCacheListener = (curatorFramework1,pathChildrenCacheEvent)->{
//            curatorFramework1.getChildren();

            //拿到触发数据变化以后的数据
            System.out.println(pathChildrenCacheEvent.getType()+"->"+new String(pathChildrenCacheEvent.getData().getData()));

        };
        nodeChildCache.getListenable().addListener(nodeCacheListener);
        nodeChildCache.start(PathChildrenCache.StartMode.NORMAL);
    }

}
