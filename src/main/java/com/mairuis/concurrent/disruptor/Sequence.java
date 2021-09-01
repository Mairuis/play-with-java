package com.mairuis.concurrent.disruptor;

import com.mairuis.jvm.util.Utils;
import sun.misc.Unsafe;

/**
 * @author Mairuis
 * @since 2021/6/17
 */

class Value {
    protected byte
            p10, p11, p12, p13, p14, p15, p16, p17,
            p20, p21, p22, p23, p24, p25, p26, p27,
            p30, p31, p32, p33, p34, p35, p36, p37,
            p40, p41, p42, p43, p44, p45, p46, p47,
            p50, p51, p52, p53, p54, p55, p56, p57,
            p60, p61, p62, p63, p64, p65, p66, p67,
            p70, p71, p72, p73, p74, p75, p76, p77;
    protected volatile long value;
    protected byte
            p90, p91, p92, p93, p94, p95, p96, p97,
            p100, p101, p102, p103, p104, p105, p106, p107,
            p110, p111, p112, p113, p114, p115, p116, p117,
            p120, p121, p122, p123, p124, p125, p126, p127,
            p130, p131, p132, p133, p134, p135, p136, p137,
            p140, p141, p142, p143, p144, p145, p146, p147,
            p150, p151, p152, p153, p154, p155, p156, p157;
}

public class Sequence extends Value {

    private static final Unsafe UNSAFE = Utils.getUnsafe();

    private static final long VALUE_OFFSET;

    static {
        try {
            VALUE_OFFSET = UNSAFE.objectFieldOffset(Value.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public Sequence() {
        this(-1);
    }

    public Sequence(long initializeValue) {
        this.set(initializeValue);
    }

    public void set(long value) {
        UNSAFE.putOrderedLong(this, VALUE_OFFSET, value);
    }

    public void setVolatile(long value) {
        UNSAFE.putLongVolatile(this, VALUE_OFFSET, value);
    }

    protected volatile long value;

    public long get() {
        return value;
    }

    public boolean compareAndSet(long expected, long newValue) {
        return UNSAFE.compareAndSwapLong(this, VALUE_OFFSET, expected, newValue);
    }

    public long incrementAndGet() {
        return addAndGet(1L);
    }

    public long addAndGet(long increment) {
        long current;
        long next;
        do {
            current = get();
            next = current + increment;
        } while (!compareAndSet(current, next));
        return next;
    }

    @Override
    public String toString() {
        return String.valueOf(get());
    }
}
