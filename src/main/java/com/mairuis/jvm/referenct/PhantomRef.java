package com.mairuis.jvm.referenct;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用测试
 *
 * @author Mairuis
 * @date 2018/12/13
 */
public class PhantomRef {

    public static final ReferenceQueue<FooClazz> fooRefQueue = new ReferenceQueue<>();
    public static FooClazz foo;
    /**
     * 是否在finalize中创建强引用来阻止foo对象被释放
     * <p>
     * 如果为true  main函数将变成无限循环 因为每一次finalize都会被强引用
     * 如果为false foo对象将被释放main函数执行结束
     */
    public static boolean preventFinalize = true;

    /**
     * 虚引用
     * 调用get方法永远返回空
     * 构造时必须指定一个引用队列,当虚引用指向的对象被释放后会将自己入队
     * 利用此机制可以知道引用对象是在什么时候被销毁的
     *
     * @return
     */
    public static Reference<FooClazz> phantomRef() {
        return new PhantomReference<>(new FooClazz(), fooRefQueue);
    }

    public static void main(String[] args) throws Exception {
        Reference<FooClazz> ref = phantomRef();
        while (true) {
            if (fooRefQueue.poll() != null) {
                System.out.println("捕捉到foo已经被释放!");
                break;
            } else {
                System.out.println("我还活着");
                System.gc();
                Thread.sleep(100);
            }
        }
        System.out.println("我死了? " + (foo == null));
    }

    protected static class FooClazz {
        /**
         * 如果finalize中又一次创建自己的强引用
         * 本次GC将无法释放这个对象且Phantom引用也不会将自己入队
         * 这就导致了有一堆garbage却迟迟不GC
         *
         * @throws Throwable
         */
        @Override
        protected void finalize() {
            if (preventFinalize) {
                System.out.println("我不能死");
                //创建强引用 使jvm无法释放自己
                PhantomRef.foo = this;
            } else {
                System.out.println("我被释放了..");
            }
        }
    }
}
