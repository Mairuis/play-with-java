package com.mairuis.algorithm.analysis;

/**
 * 秒表
 *
 * @author Mairuis
 * @date 2019/3/19
 */
public class Watch {

    private long start;

    public void begin() {
        this.start = System.currentTimeMillis();
    }

    public float end() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000F;
    }
}
