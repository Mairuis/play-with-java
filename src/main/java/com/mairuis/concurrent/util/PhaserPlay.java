package com.mairuis.concurrent.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * https://www.baeldung.com/java-phaser
 *
 * @author Mairuis
 * @since 2020/5/16
 */
public class PhaserPlay {

    public static void main(String[] args) {
        int threadCount = 3;
        Phaser phaser = new Phaser(threadCount) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                System.out.println("阶段 " + phase + " 已完成，参与数 " + registeredParties);
                return registeredParties == 0;
            }
        };
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                for (int j = 0; j < threadCount; j++) {
                    phaser.register();
                    System.out.println(Thread.currentThread().getName() + " 完成 " + j);
                    phaser.arriveAndAwaitAdvance();
                }
            }).start();
        }
    }

}
