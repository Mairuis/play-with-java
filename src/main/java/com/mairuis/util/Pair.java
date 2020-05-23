package com.mairuis.util;

/**
 * @author Mairuis
 * @since 2020/5/5
 */
public class Pair<L, R> {
    private L l;
    private R r;

    public Pair(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public L getLeft() {
        return l;
    }

    public R getRight() {
        return r;
    }

    public Pair<L, R> setLeft(L l) {
        this.l = l;
        return this;
    }

    public Pair<L, R> setRight(R r) {
        this.r = r;
        return this;
    }
}
