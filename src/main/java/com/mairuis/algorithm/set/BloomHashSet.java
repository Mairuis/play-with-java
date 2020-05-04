package com.mairuis.algorithm.set;

import java.util.BitSet;

/**
 * 布隆过滤器简单实现
 *
 * @author Mairuis
 * @date 2019/7/17
 */
public class BloomHashSet {

    private final int size;
    private BitSet bitSet;

    public BloomHashSet(int size) {
        this.size = size;
        //由于我们有两个hash函数，这样可以降低碰撞率
        this.bitSet = new BitSet(size * 2);
    }

    public static void main(String[] args) {
        System.out.println(new String());
    }

    public boolean put(Object o) {
        int h1 = hash0(o) % size;
        int h2 = hash1(o) % size;
        if (bitSet.get(h1) && bitSet.get(h2)) {
            return false;
        } else {
            bitSet.set(h1);
            bitSet.set(h2);
        }
        return true;
    }

    public boolean get(Object o) {
        int h1 = hash0(o) % size;
        int h2 = hash1(o) % size;
        return bitSet.get(h1) && bitSet.get(h2);
    }

    private int hash0(Object o) {
        return o.hashCode();
    }

    private int hash1(Object o) {
        return o.hashCode() & size;
    }
}
