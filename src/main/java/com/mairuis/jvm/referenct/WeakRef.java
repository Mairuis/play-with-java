package com.mairuis.jvm.referenct;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.locks.LockSupport;

/**
 * Java中一共四种引用
 * 强引用,在引用没有失效之前永远不会被回收
 * Object a = new Object();
 * 软引用,只有内存不足且没有强引用指向该对象时才会被释放
 * new SoftReference<>(new Object());
 * 弱引用,没有强引用指向该对象则释放
 * new WeakReference<>(new Object());
 * 虚引用,不会对生存时间构成影响,也不能通过引用获得该对象,唯一作用就是当对象被回收时收到一个通知
 * <p>
 * Finalizer线程
 * 这是一个后台线程,负责执行finalize方法,当对象被标记为可以被回收的同时也会被
 * 判断是否可以执行finalize方法,如果可以,这个对象会被放入F-Queue队列中,若果执
 * 行时发生了死循环,F-Queue中的其他对象将永远处于等待,甚至可能导致整个内存回收系统崩溃.
 * 判断是否可以执行finalize有的条件
 * 是否重写finalize,是否已执行过一次finalize(对象曾在finalize中创建强引用自救)
 *
 * @author Mairuis
 * @date 2018/12/13
 * @see PhantomRef
 */
public class WeakRef {

    /**
     * 弱引用 没有强引用指向该对象则释放
     *
     * @return
     */
    public static Reference<FooClazz> weakRef() {
        return new WeakReference<>(new FooClazz());
    }

    /**
     * 软引用 只有内存不足且没有强引用指向该对象时才会被释放
     *
     * @return
     */
    public static Reference<FooClazz> softRef() {
        return new SoftReference<>(new FooClazz());
    }

    public static void main(String[] args) throws InterruptedException {
        Reference<FooClazz> fooRef = weakRef();
        while (true) {
            if (fooRef.get() != null) {
                System.out.println("我还活着");
            } else {
                System.out.println("我凉了");
                Thread.sleep(1000);
                System.out.println("走了");
                break;
            }
        }
    }

    static class FooClazz {

        @Override
        protected void finalize() {
            System.out.println(Thread.currentThread().getName() + " 线程来回收我了");
            System.out.println(System.currentTimeMillis());
            LockSupport.park();
            System.out.println("我阻塞中断了");
        }

    }
}
