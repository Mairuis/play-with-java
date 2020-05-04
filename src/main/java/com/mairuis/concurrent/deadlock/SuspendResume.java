package com.mairuis.concurrent.deadlock;

/**
 * 为啥resume和suspend被弃用？
 *
 * @author Mairuis
 * @date 2019/7/20
 */
public class SuspendResume {

    static volatile Object item = null;

    public static void main(String[] args) throws InterruptedException {
        Thread consumer = new Thread(() -> {
            if (item == null) {
                System.out.println("1.没东西进入等待");

                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                }
                Thread.currentThread().suspend();
            }
            System.out.println("2，东西到手");
        });
        consumer.start();
        Thread.sleep(1000);
        item = new Object();
        System.out.println("生产");
        consumer.resume();
        consumer.join();


    }

}
