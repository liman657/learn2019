package com.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/9/4
 * comment: Curator权限信息
 */
public class CuratorACLDemo {

    private static String connectionStr = "127.0.0.1:2181";
    public static void main(String[] args) throws Exception {

        //建立连接
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectionStr).sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        curatorFramework.start();//启动

        createNodeWithACL(curatorFramework);

        updateAclInExistNode(curatorFramework);

    }

    public static void createNodeWithACL(CuratorFramework curatorFramework) throws Exception {
        List<ACL> aclList = new ArrayList<ACL>();
        /**
         * zk提供了6种操作权限
         *
         * Id——需要一个schema和一个id
         * zk提供了几种权限模式，schema就是几种权限模式
         * IP/Digest(用户密码的形式，此时id指为用户名和密码)/world(开放模式)/super(超级用户)
         */
        //这句的意思就是，给admin用户增加了一个读的权限
        ACL acl =new ACL(ZooDefs.Perms.READ| ZooDefs.Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("admin:admin")));
        aclList.add(acl);

        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(aclList).forPath("/test/auth");
    }

    /**
     * 给一个已经存在的节点设置权限
     */
    private static void updateAclInExistNode(CuratorFramework curatorFramework) throws Exception {
        List<ACL> aclList = new ArrayList<ACL>();
        /**
         * zk提供了6种操作权限
         *
         * Id——需要一个schema和一个id
         * zk提供了几种权限模式，schema就是几种权限模式
         * IP/Digest(用户密码的形式，此时id指为用户名和密码)/world(开放模式)/super(超级用户)
         */
        //这句的意思就是，给admin用户增加了一个读的权限
        ACL acl =new ACL(ZooDefs.Perms.READ | ZooDefs.Perms.WRITE,new Id("digest",DigestAuthenticationProvider.generateDigest("admin:admin")));
        aclList.add(acl);
        curatorFramework.setACL().withACL(aclList).forPath("/test/auth");

//        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).withACL(aclList).forPath("/test/auth");
    }


}
