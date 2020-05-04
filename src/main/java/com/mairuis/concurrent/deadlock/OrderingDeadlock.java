package com.mairuis.concurrent.deadlock;

/**
 * 顺序死锁演示
 *
 * @author Mairuis
 * @date 2019/1/8
 */
public class OrderingDeadlock {

    static Object aLock = new Object(), bLock = new Object();

    public static void thread1() {
        while (true) {
            synchronized (aLock) {
                synchronized (bLock) {
                    System.out.println("A 逃过一劫");
                }
            }
        }
    }


    public static void thread2() {
        while (true) {
            synchronized (bLock) {
                synchronized (aLock) {
                    System.out.println("B 逃过一劫");
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(OrderingDeadlock::thread1);
        Thread t2 = new Thread(OrderingDeadlock::thread2);
        t.start();
        t2.start();
    }
}
