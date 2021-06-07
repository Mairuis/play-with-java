package com.mairuis.concurrent.falseshare;


/**
 * 对于HotSpot JVM，所有对象都有两个字长的对象头。
 * 第一个字是由24位哈希码和8位标志位（如锁的状态或作为锁对象）组成的Mark Word。
 * 第二个字是对象所属类的引用。
 * 如果是数组对象还需要一个额外的字来存储数组的长度。
 * 每个对象的起始地址都对齐于8字节以提高性能。
 * 因此当封装对象的时候为了高效率，对象字段声明的顺序会被重排序成下列基于字节大小的顺序：
 * doubles (8) 和 longs (8)
 * ints (4) 和 floats (4)
 * shorts (2) 和 chars (2)
 * booleans (1) 和 bytes (1)
 * references (4/8)
 * <子类字段重复上述顺序>
 * <p>
 * <p>
 * 伪共享
 * 一种CPU缓存读取机制和缓存一致性协议引发的性能骤降。
 * 解决方案:
 * <p>
 * 1.在字段前面填充8个long变量，保证不和读取字段前面的变量一起读到同一个缓存行，但不同CPU架构可能缓存行大小不一样
 * 2.使用@Contended注解声明类或字段，该方法弥补了人工填充的缺点，其原理就是让VM根据不同平台自动缓存填充
 * </p>
 * -XX:+PrintFieldLayout -XX:-RestrictContended
 * 论文地址 http://mechanical-sympathy.blogspot.com/2011/07/false-sharing.html
 *
 * @author Mairuis
 * @date 2019/7/26
 */
public final class FalseSharing implements Runnable {
    public final static int NUM_THREADS = 8;
    public final static int ITERATIONS = 1024 * 1024;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    private final int arrayIndex;

    public FalseSharing(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("duration = " + (System.currentTimeMillis() - start));
    }

    @Override
    public void run() {
        int i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    /**
     * 并不能完美保证不发生伪共享
     * 因为缓存行可能从任意一个位置开始
     */
    public final static class VolatileLong {
        //填充64字节的前56个
        protected long p1, p2, p3, p4, p5, p6, p7;
        //填充缓存行最后一个
        public volatile long value;
        //填充 value所在的缓存行
        protected long p9, p10, p11, p12, p13, p14, p15;
    }


    public final static class VolatileInt {
        //新版JDK不让玩了 ？？？ 啧啧
//        @jdk.internal.vm.annotation.Contended("tlr")
        public volatile int value = 0;
    }
}