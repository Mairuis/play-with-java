package com.mairuis.zookeeper.dl;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主线程上来先请求锁，然后创建十个线程去拿同一锁，创建完成后释放锁
 * 十个线程对integer做一次自增操作，然后释放锁退出
 * @author Mairuis
 * @since 2020/6/24
 */
public class CuratorDistributeLock {

    public static void main(String[] args) throws Exception {
        TestingServer server = new TestingServer(2181);
        server.start();

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .retryPolicy(new ExponentialBackoffRetry(500, 3, 3000))
                .connectString(server.getConnectString())
                .build();
        curatorFramework.start();
        InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/test/mutex/lock");
        interProcessMutex.acquire();
        final int n = 10;
        final CountDownLatch latch = new CountDownLatch(n);
        final AtomicInteger integer = new AtomicInteger(0);
        final List<Thread> threads = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(() -> {
                try {
                    latch.countDown();
                    interProcessMutex.acquire();
                    integer.set(integer.get() + 1);
                    System.out.println("加 1");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        interProcessMutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }
        latch.await();
        interProcessMutex.release();
        threads.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("current value " + integer.get());
        server.close();
    }

}
