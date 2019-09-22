package com.learn.curator.update;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.time.LocalDateTime;
import java.util.List;

/**
 * author:liman
 * createtime:2019/9/17
 * comment:
 */
public class CuratorLock {

    private static final String connStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder().connectString(connStr).sessionTimeoutMs(5000000)
                .namespace("learn")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();

        final InterProcessMutex lock=new InterProcessMutex(curatorFramework,"/locks");

        for(int i=0;i<10;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"->尝试竞争锁");
                try {
                    lock.acquire(); //阻塞竞争锁

                    System.out.println(Thread.currentThread().getName()+"->成功获得了锁");
                    getChildNodeData(curatorFramework);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        lock.release(); //释放锁
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"Thread-"+i).start();
        }

        System.in.read();

        closeConn(curatorFramework);

    }

    public static void closeConn(CuratorFramework curatorFramework) {
        curatorFramework.close();
    }

    /**
     * 获取子节点数据
     * @param curatorFramework
     * @throws Exception
     */
    public static void getChildNodeData(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/locks";
        List<String> children = curatorFramework.getChildren().forPath(nodePath);
        System.out.println("开始打印子节点信息:");
        children.forEach(System.out::println);
        System.out.println("打印结束");
    }

}
