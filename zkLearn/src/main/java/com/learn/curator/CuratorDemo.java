package com.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * autor:liman
 * createtime:2019/9/4
 * comment:Curator实例
 */
public class CuratorDemo {


    private static String connectionStr = "127.0.0.1:2181";
    public static void main(String[] args) throws Exception {

        //建立连接
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectionStr).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        curatorFramework.start();//启动

//        curatorCreateData(curatorFramework);
//        curatorUpdateNodeData(curatorFramework);
        curatorDeleteNodeData(curatorFramework);
    }

    /**
     * 创建节点
     */
    public static void curatorCreateData(CuratorFramework curatorFramework) throws Exception {
        //creatingParentsIfNeeded指定创建父节点。
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/data/test","create".getBytes());
    }

    public static void curatorUpdateNodeData(CuratorFramework curatorFramework) throws Exception {
        curatorFramework.setData()
                .forPath("/data/test","update".getBytes());
    }

    public static void curatorDeleteNodeData(CuratorFramework curatorFramework) throws Exception {
        //动态获取版本号
        Stat stat = new Stat();
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/data/test");
        String statInfo = new String(bytes);
        System.out.println(statInfo);
        curatorFramework.delete().withVersion(stat.getVersion())
                .forPath("/data/test");
    }

}
