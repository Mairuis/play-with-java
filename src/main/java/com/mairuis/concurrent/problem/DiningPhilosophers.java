package com.mairuis.concurrent.problem;


import com.mairuis.concurrent.util.Threads;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * 哲学家就餐问题
 * 5个人，5个叉子
 *
 * @author Mairuis
 * @since 2020/5/3
 */
public abstract class DiningPhilosophers {

    public static void main(String[] args) throws InterruptedException {
        DiningPhilosophers diningPhilosophers = new CASImplementSolution();
        long time = System.currentTimeMillis();
        Threads.start(100, (i) -> {
            int N = 100000;
            while (N > 0) {
                diningPhilosophers.eat(i % 5);
                N -= 1;
            }
        }).join();
        long passTime = System.currentTimeMillis() - time;
        System.out.println("success " + diningPhilosophers.count() + " 耗时 " + passTime + "ms");
    }

    public abstract void eat(int philosophers);

    public abstract int count();

    /**
     * 尝试拿起第二把叉子，如果失败则全部释放的解法
     */
    public static class CASImplementSolution extends DiningPhilosophers {
        private final AtomicBoolean[] forks = IntStream.range(0, 6).mapToObj(x -> new AtomicBoolean(false)).toArray(AtomicBoolean[]::new);
        private final AtomicInteger eatCount = new AtomicInteger(0);

        @Override
        public void eat(int philosophers) {
            AtomicBoolean right = forks[philosophers];
            AtomicBoolean left = forks[philosophers - 1 < 0 ? forks.length - 1 : philosophers - 1];
            for (; ; ) {
                if (!right.compareAndSet(false, true)) {
                    continue;
                }
                if (!left.compareAndSet(false, true)) {
                    right.compareAndSet(true, false);
                    continue;
                }
                left.compareAndSet(true, false);
                right.compareAndSet(true, false);
                break;
            }
            eatCount.incrementAndGet();
        }

        @Override
        public int count() {
            return eatCount.get();
        }
    }
}
