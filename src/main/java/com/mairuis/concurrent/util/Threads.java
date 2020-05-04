package com.mairuis.concurrent.util;

import java.util.function.IntConsumer;

/**
 * @author Mairuis
 * @since 2020/5/3
 */
public class Threads {

    private final Thread[] threads;

    private Threads(int n, IntConsumer runnable) {
        this.threads = new Thread[n];
        for (int i = 0; i < threads.length; i += 1) {
            int finalI = i;
            this.threads[i] = new Thread(() -> {
                runnable.accept(finalI);
            }, "T-" + i);
            this.threads[i].start();
        }
    }

    public static Threads start(int n, IntConsumer runnable) {
        return new Threads(n, runnable);
    }

    public void join() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
