package com.mairuis.algorithm.analysis;

import java.util.Random;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/3/19
 */
public class ThreeSum {

    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt += 1;
                    }
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int N = 300000;
        int MAX = 10000;
        int[] a = new int[N];

        for (int i = 0; i < N; i++) {
            a[i] = random.nextInt(MAX);
        }
        Watch watch = new Watch();
        watch.begin();
        count(a);
        System.out.println(watch.end());
    }
}
