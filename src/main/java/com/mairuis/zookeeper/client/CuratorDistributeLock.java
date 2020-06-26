package com.mairuis.zookeeper.client;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mairuis
 * @since 2020/6/24
 */
public class CuratorDistributeLock {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(500, 3, 3000))
                .connectString("localhost:2181")
                .build();
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/test/mutex/lock");
        ReentrantLock startLock = new ReentrantLock();
        final Condition started = startLock.newCondition();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    started.await();
                    interProcessMutex.acquire();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
