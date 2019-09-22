package com.learn.curator.master;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * autor:liman
 * createtime:2019/9/21
 * comment:
 */
public class LeaderSelectorClientThree extends LeaderSelectorListenerAdapter implements Closeable {

    private static final String connStr = "127.0.0.1:2181";

    private String processName;
    private LeaderSelector leaderSelector;
    private CountDownLatch countDownLatch =new CountDownLatch(1);

    public LeaderSelectorClientThree(String processName) {
        this.processName = processName;
    }

    public LeaderSelector getLeaderSelector() {
        return leaderSelector;
    }

    public void setLeaderSelector(LeaderSelector leaderSelector) {
        this.leaderSelector = leaderSelector;
    }

    public void start(){
        leaderSelector.start();//开始竞争leader
    }

    @Override
    public void close() throws IOException {
        leaderSelector.close();
    }

    @Override
    public void takeLeadership(CuratorFramework client) throws Exception {
        //如果进入当前的方法，意味着当前的进程获得了锁。获得锁以后，这个方法会被回调
        //这个方法执行结束之后，表示释放leader权限
        System.out.println(processName+" 已经是leader了");
        countDownLatch.await();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(connStr).sessionTimeoutMs(50000000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
        curatorFramework.start();
        LeaderSelectorClientThree leaderSelectorClient=new LeaderSelectorClientThree("ClientC");
        LeaderSelector leaderSelector=new LeaderSelector(curatorFramework,"/leader",leaderSelectorClient);
        leaderSelectorClient.setLeaderSelector(leaderSelector);
        Thread.sleep(1000);
        leaderSelectorClient.start(); //开始选举

        System.in.read();
    }
}
