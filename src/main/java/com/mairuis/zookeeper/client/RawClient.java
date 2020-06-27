package com.mairuis.zookeeper.client;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

/**
 * 原生客户端连接小demo
 *
 * @author Mairuis
 * @since 2020/6/21
 */
public class RawClient implements Watcher {

    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("收到事件: " + watchedEvent);
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            countDownLatch.countDown();
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        RawClient watcher = new RawClient();
        try (ZooKeeper zooKeeper = new ZooKeeper("localhost:2181"
                , 1000
                , watcher)) {
            System.out.println("初始化后的状态: " + zooKeeper.getState());
            watcher.countDownLatch.await();
            System.out.println("连接成功");
            String result = zooKeeper.create("/test", ByteBuffer.allocate(4).putInt(2333).array(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("创建成功: " + result);
        }
    }
}
