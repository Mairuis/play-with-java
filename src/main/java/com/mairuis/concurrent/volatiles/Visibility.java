package com.mairuis.concurrent.volatiles;

/**
 * 线程可见性
 * https://stackoverflow.com/questions/5816790/the-code-example-which-can-prove-volatile-declare-should-be-used
 *
 * @author Mairuis
 * @since 2021/6/14
 */
public class Visibility {

    /**
     * 如果把volatile移除会有神器的事情发生
     */
    static volatile boolean running = true;

    public static void main(String[] args) {
        new Thread(() -> {
            int counter = 0;
            while (running) {
                counter++;
            }
            System.out.println("Thread 1 finished. Counted up to " + counter);
        }).start();
        new Thread(() -> {
            //睡眠确保thread1大概率先被调度
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Thread 2 finishing");
            running = false;
        }).start();

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
