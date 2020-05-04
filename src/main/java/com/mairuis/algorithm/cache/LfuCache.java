package com.mairuis.algorithm.cache;

/**
 * TODO 淘汰最近使用次数最少的
 *
 * @author Mairuis
 * @date 2019/7/17
 */
public class LfuCache {

    private int capacity;


    public LfuCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        return -1;
    }

    public void put(int key, int value) {

    }
}
