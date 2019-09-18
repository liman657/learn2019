package com.learn.curator.update;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * author:liman
 * createtime:2019/9/11
 * comment:curator的连接操作
 */
public class CuratorConn {

    private static final String connStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder().connectString(connStr).sessionTimeoutMs(5000)
                .namespace("learn")
                .retryPolicy(new ExponentialBackoffRetry(1000,3))
                .build();
        curatorFramework.start();

        deleteNodeData(curatorFramework);

//        getNode(curatorFramework);

//        getChildNodeData(curatorFramework);

//        updateNodeData(curatorFramework);

//        deleteNodeData(curatorFramework);

        closeConn(curatorFramework);
    }

    public static void closeConn(CuratorFramework curatorFramework){
        curatorFramework.close();
    }

    /**
     * 创建节点
     * @param curatorFramework
     * @throws Exception
     */
    public static void createNode(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/node/create";
        byte[] data = "createData".getBytes();
        curatorFramework.create().creatingParentsIfNeeded()//递归创建节点
                .withMode(CreateMode.PERSISTENT)//创建持久性节点
                .forPath(nodePath,data);//指定创建的内容
    }

    /**
     * 获取指定节点的数据
     * @param curatorFramework
     * @throws Exception
     */
    public static void getNode(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/node/create";
        byte[] bytes = curatorFramework.getData().forPath(nodePath);
        System.out.println("第一次获得节点的数据为:"+new String(bytes));

        Stat stat = new Stat();//新建一个Stat对象，查询出来的节点属性会保存在这个对象中
        byte[] statBytes = curatorFramework
                .getData()
                .storingStatIn(stat)//传入一个旧的stat对象，用来存储服务端返回的最新节点的状态信息
                .forPath(nodePath);
        System.out.println("第二次获取节点的数据，包括状态数据:"+new String(statBytes));
        System.out.println("获得到的Stat为:"+stat.toString());
    }

    /**
     * 获取子节点数据
     * @param curatorFramework
     * @throws Exception
     */
    public static void getChildNodeData(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/node";
        List<String> children = curatorFramework.getChildren().forPath(nodePath);
        System.out.println("开始打印子节点信息:");
        children.forEach(System.out::println);
        System.out.println("打印结束");
    }

    /**
     * 修改节点数据
     * @param curatorFramework
     */
    public static void updateNodeData(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/node/create";
        Stat stat = curatorFramework.setData().forPath(nodePath, "update01".getBytes());
        System.out.println("第一次修改后的数据为："+new String(curatorFramework.getData().forPath(nodePath)));

        Thread.sleep(50000);

        Stat stat02 = curatorFramework.setData()
                .withVersion(stat.getVersion())//指定版本信息修改节点的数据
                .forPath(nodePath, "update02".getBytes());
        System.out.println("利用版本号修改后的数据为："+new String(curatorFramework.getData().forPath(nodePath)));
    }

    /**
     * 删除节点数据
     * @param curatorFramework
     */
    public static void deleteNodeData(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/";
        Stat stat = new Stat();
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath(nodePath);
        System.out.println("获取到的节点的数据为："+new String(bytes));
        curatorFramework.delete()
                .guaranteed()
                .deletingChildrenIfNeeded()
                .withVersion(stat.getVersion())
                .forPath(nodePath);
    }

//    public static void isExist(CuratorFramework curatorFramework){
//        curatorFramework.checkExists().forPath();
//    }

}
