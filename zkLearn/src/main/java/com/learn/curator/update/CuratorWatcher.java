package com.learn.curator.update;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * author:liman
 * createtime:2019/9/17
 * comment: curator的watcher机制的实例。
 * <p>
 * Curator 事件有两种模式，一种是标准的观察模式，一种是缓存监听模式。
 * 标准的监听模式是使用Watcher 监听器。第二种缓存监听模式引入了一种本地缓存视图的Cache机制，来实现对Zookeeper服务端事件监听。
 * <p>
 * Cache事件监听可以理解为一个本地缓存视图与远程Zookeeper视图的对比过程。Cache提供了反复注册的功能。
 * Cache是一种缓存机制，可以借助Cache实现监听。
 * 简单来说，Cache在客户端缓存了znode的各种状态，当感知到zk集群的znode状态变化，会触发event事件，注册的监听器会处理这些事件。
 * <p>
 * Watcher 监听器比较简单，只有一种。Cache事件监听的种类有3种Path Cache，Node Cache，Tree Cache。
 */
public class CuratorWatcher {

    private static final String connStr = "127.0.0.1:2181";

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .builder().connectString(connStr).sessionTimeoutMs(5000)
                .namespace("learn")
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        curatorFramework.start();

//        testUsingWatcher(curatorFramework);
//        testNodeCache(curatorFramework);
        testTreeCacheNode(curatorFramework);
        closeConn(curatorFramework);

    }

    public static void closeConn(CuratorFramework curatorFramework) {
        curatorFramework.close();
    }

    public static void testUsingWatcher(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        //这个监听事件只能触发一次
        curatorFramework.getData().usingWatcher((org.apache.curator.framework.api.CuratorWatcher) watchedEvent -> {
            System.out.println("触发了watcher事件，节点路径为:" + watchedEvent.getPath() + ",事件类型为:" + watchedEvent.getType());
        }).forPath(nodePath);
//        System.in.read();
    }

    /**
     * 既然Watcher监听器是一次性的，在开发过程中需要反复注册Watcher，比较繁琐。
     * Curator引入了Cache来监听ZooKeeper服务端的事件。
     * Cache对ZooKeeper事件监听进行了封装，能够自动处理反复注册监听。
     *
     * @param curatorFramework
     * @throws Exception
     */
    public static void testUsingWatcherUpdate(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        Watcher watcher = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("触发了watcher事件，节点路径为:" + watchedEvent.getPath() + ",事件类型为:" + watchedEvent.getType());
            }
        };

        curatorFramework.getData().usingWatcher(watcher).forPath(nodePath);
    }

    /**
     * Curator引入的Cache缓存实现，是一个系列，包括了Node Cache 、Path Cache、Tree Cache三组类。
     * 其中Node Cache节点缓存可以用于ZNode节点的监听，
     * Path Cache子节点缓存用于ZNode的子节点的监听，
     * 而Tree Cache树缓存是Path Cache的增强，不光能监听子节点，也能监听ZNode节点自身。
     *
     * @param curatorFramework
     */
    public static void testNodeCache(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        //这个监听事件只能触发一次
//        curatorFramework.getData().usingWatcher((org.apache.curator.framework.api.CuratorWatcher) watchedEvent -> {
//            System.out.println("触发了watcher事件，节点路径为:"+watchedEvent.getPath()+",事件类型为:"+watchedEvent.getType());
//        }).forPath(nodePath);

        //NodeCache会监听数据的变更，会触发事件，第三个参数为boolean类型，默认为false，如果设置为true，则开启节点缓存
        NodeCache nodeCache = new NodeCache(curatorFramework, nodePath, false);
        nodeCache.start(true);
        if (nodeCache.getCurrentData() != null) {
            System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("节点数据为空");
        }

        //添加事件
        nodeCache.getListenable().addListener(() -> {
            String data = new String(nodeCache.getCurrentData().getData());
            System.out.println("节点路径：" + nodeCache.getCurrentData().getPath() + "，节点数据为：" + new String(nodeCache.getCurrentData().getData()));
        });
        System.in.read();
    }

    public static void testNodeCacheUpdate(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";

        //1.初始化一个nodeCache
        NodeCache nodeCache = new NodeCache(curatorFramework, nodePath, false);

        //2.初始化一个NodeCacheListener
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                ChildData childData = nodeCache.getCurrentData();
                System.out.println("ZNode 节点的状态变化,path=" + childData.getPath());
                System.out.println("ZNode 节点的状态变化,data=" + new String(childData.getData(), "utf-8"));
                System.out.println("ZNode 节点的状态变化,stat=" + childData.getStat());
            }
        };

        nodeCache.getListenable().addListener(nodeCacheListener);
        /**
         * 唯一的一个参数buildInitial代表着是否将该节点的数据立即进行缓存。
         * 如果设置为true的话，在start启动时立即调用NodeCache的getCurrentData方法就能够得到对应节点的信息ChildData类，
         * 如果设置为false的就得不到对应的信息。
         *
         * 使用NodeCache来监听节点的事件
         */
        nodeCache.start();//start

        // 第1次变更节点数据
        curatorFramework.setData().forPath(nodePath, "第1次更改内容".getBytes());
        Thread.sleep(1000);

        // 第2次变更节点数据
        curatorFramework.setData().forPath(nodePath, "第2次更改内容".getBytes());

        Thread.sleep(1000);

        // 第3次变更节点数据
        curatorFramework.setData().forPath(nodePath, "第3次更改内容".getBytes());
        Thread.sleep(1000);

        System.in.read();

        // 第4次变更节点数据
//            client.delete().forPath(workerPath);
    }

    /**
     * @param curatorFramework
     */
    public static void testNodeCacheDelete(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        NodeCache nodeCache = new NodeCache(curatorFramework, nodePath);
        nodeCache.start(true);
        if (nodeCache.getCurrentData() != null) {
            System.out.println("节点初始化数据为：" + new String(nodeCache.getCurrentData().getData()));
        } else {
            System.out.println("节点数据为空!");
        }

        nodeCache.getListenable().addListener(() -> {
            System.out.println(LocalDateTime.now() + "=============触发了节点事件");
        });

        System.out.println("开始创建节点");
        curatorFramework.create()
                .creatingParentsIfNeeded()//递归创建节点
                .withMode(CreateMode.PERSISTENT)
                .forPath(nodePath, "new Time".getBytes());
//                .withACL()

        System.in.read();
    }

    public static void testChildrenCache(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        PathChildrenCache childrenCache = new PathChildrenCache(curatorFramework, nodePath, true);
        childrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);//初始化有几种方式，同步与异步，TODO：具体
        List<ChildData> childDataList = childrenCache.getCurrentData();
        System.out.println("子节点数据为:");
        childDataList.forEach(childData -> {
            System.out.println(new String(childData.getData()));
        });
        System.in.read();
    }

    /**
     * 测试pathChildrenCache
     * PathChildrenCache子节点缓存用于子节点的监听，监控本节点的子节点被创建、更新或者删除
     * （1）只能监听子节点，监听不到当前节点
     * <p>
     * （2）不能递归监听，子节点下的子节点不能递归监控
     * <p>
     * 简单说下Curator的监听原理，无论是PathChildrenCache，还是TreeCache，
     * 所谓的监听，都是进行Curator本地缓存视图和ZooKeeper服务器远程的数据节点的对比
     * <p>
     * 以节点增加事件NODE_ADDED为例，所在本地缓存视图开始的时候，本地视图为空，
     * 在数据同步的时候，本地的监听器就能监听到NODE_ADDED事件。
     * 这是因为，刚开始本地缓存并没有内容，然后本地缓存和服务器缓存进行对比，发现ZooKeeper服务器有节点而本地缓存没有，
     * 这才将服务器的节点缓存到本地，就会触发本地缓存的NODE_ADDED事件。
     *
     * @param curatorFramework
     */
    public static void testPathChildrenCache(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, nodePath, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                ChildData childData = pathChildrenCacheEvent.getData();
                switch (pathChildrenCacheEvent.getType()) {
                    case CHILD_ADDED:
                        System.out.println("子节点增加，path=" + childData.getPath() + ",data=" + new String(childData.getData(), "utf-8"));
                        break;
                    case CHILD_UPDATED:
                        System.out.println("子节点更新，path=" + childData.getPath() + ",data=" + new String(childData.getData(), "utf-8"));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("子节点删除，path=" + childData.getPath() + ",data=" + new String(childData.getData(), "utf-8"));
                        break;
                    default:
                        break;
                }
            }
        };

        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            String childPath = nodePath + "/" + i;
            byte[] data = childPath.getBytes();
            curatorFramework.create().forPath(childPath, data);
        }

        Thread.sleep(100000);
        for (int i = 0; i < 3; i++) {
            String childPath = nodePath + "/" + i;
            curatorFramework.delete().forPath(childPath);
        }
    }

    /**
     * Tree Cache可以看做是上两种的合体，
     * Tree Cache观察的是当前ZNode节点的所有数据。
     * 而TreeCache节点树缓存是PathChildrenCache的增强，
     * 不光能监听子节点，也能监听节点自身。
     *
     * @param curatorFramework
     */
    public static void testTreeCacheNode(CuratorFramework curatorFramework) throws Exception {
        String nodePath = "/watcher";
        TreeCache treeCache = new TreeCache(curatorFramework, nodePath);
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                ChildData data = treeCacheEvent.getData();
                switch (treeCacheEvent.getType()) {
                    case NODE_ADDED:
                        System.out.println("[TreeNode]节点增加，path="+data.getPath()+",data="+new String(data.getData(),"utf-8"));
                        break;
                    case NODE_UPDATED:
                        System.out.println("[TreeNode]节点更新，path="+data.getPath()+",data="+new String(data.getData(),"utf-8"));
                        break;
                    case NODE_REMOVED:
                        System.out.println("[TreeNode]节点删除，path="+data.getPath()+",data="+new String(data.getData(),"utf-8"));
                        break;
                    default:
                        break;
                }
            }
        };

        treeCache.getListenable().addListener(treeCacheListener);

        treeCache.start();
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            String childPath = nodePath + "/" + i;
            byte[] data = childPath.getBytes();
            curatorFramework.create().creatingParentsIfNeeded().forPath(childPath, data);
        }

        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            String childPath = nodePath + "/" + i;
            curatorFramework.delete().forPath(childPath);
        }

        curatorFramework.setData().forPath(nodePath,"update parent".getBytes());
    }
}
