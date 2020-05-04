package com.mairuis.concurrent.deadlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 活跃死锁
 * <p>
 * 就像是两个互相谦让的人 发现和对方走的是同一条路 两人同时让出位置 发现又走的是同一条路 又让出位置 依次循环
 *
 * @author Mairuis
 * @date 2019/1/10
 */
public class Livelock {
    static ReentrantLock lock = new ReentrantLock();

    public static void thread1() {
        while (true) {
            try {
                if (lock.tryLock(1, TimeUnit.NANOSECONDS)) {
                    System.out.println("A 拿到了");
                } else {
                    System.out.println("A 让出");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void thread2() {
        while (true) {
            try {
                if (lock.tryLock(1, TimeUnit.NANOSECONDS)) {
                    System.out.println("B 拿到了");
                } else {
                    System.out.println("B 让出");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(Livelock::thread1);
        Thread t2 = new Thread(Livelock::thread2);
        t.start();
        t2.start();
    }
}
