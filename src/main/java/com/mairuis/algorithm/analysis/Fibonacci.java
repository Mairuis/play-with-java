package com.mairuis.algorithm.analysis;

import java.util.HashMap;

/**
 * 从这里开始吧
 *
 * @author Mairuis
 * @date 2018/5/6
 */
public class Fibonacci {

    private static HashMap<Long, Long> cache = new HashMap<>();

    public static long fib(long n) {
        if (cache.containsKey(n)) {
            return cache.get(n);
        }
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        long a = fib(n - 1);
        long b = fib(n - 2);
        if (!cache.containsKey(n)) {
            cache.put(n, a + b);
        }
        return a + b;
    }

    public static long fib2(long n) {
        long a = 0, b = 1;
        for (long i = 2; i <= n; i += 1) {
            long newB = a + b;
            a = b;
            b = newB;
        }
        return b;
    }

    public static int climbing(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climbing(n - 1) + climbing(n - 2);
    }

    public static void main(String[] args) {
        System.out.println(climbing(3));
    }

}
