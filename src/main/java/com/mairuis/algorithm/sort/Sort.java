package com.mairuis.algorithm.sort;

import com.mairuis.algorithm.analysis.Watch;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/1/21
 */

public abstract class Sort implements SortArray {

    private static long START;

    public static int[] generalRandomIntegers(int number) {
        int[] data = new int[number];
        for (int i = 0; i < data.length; i++) {
            data[i] = new Random().nextInt(number);
        }
        return data;
    }

    public static int[] generalLinearlyIntegers(int number) {
        int[] data = new int[number];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        return data;
    }

    public static int[] generalNearlySortedIntegers(int number) {
        int[] data = new int[number];
        for (int i = 0; i < data.length; i++) {
            data[i] = i;
        }
        for (int i = 0; i < data.length / (data.length / 10); i++) {
            int r = i + (int) (Math.random() * (data.length - i));
            int swap = data[i];
            data[i] = data[r];
            data[r] = swap;
        }
        return data;
    }

    public static boolean isSorted(int[] data) {
        boolean ascending = true, descending = true;
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                descending = false;
            } else if (data[i] < data[i + 1]) {
                ascending = false;
            }
            if (!ascending && !descending) {
                return false;
            }
        }
        return true;
    }

    public static int[] bubbleSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            boolean swap = false;
            for (int j = 0; j < data.length; j++) {
                if (data[j] > data[i]) {
                    int temp = data[j];
                    data[j] = data[i];
                    data[i] = temp;
                    swap = true;
                }
            }
            if (!swap) {
                break;
            }
        }
        return data;
    }

    public static void testSort() {
        testSort(10, false);
    }

    public static void testSort(int num, boolean nearlySoted) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerName = trace[trace.length - 1].getClassName();
        try {
            Class clazz = Class.forName(callerName);
            if (!SortArray.class.isAssignableFrom(clazz)) {
                System.out.println("调用者必须实现" + SortArray.class.getSimpleName() + "接口");
                return;
            }
            Watch watch = new Watch();
            watch.begin();
            int[] sorted = (int[]) clazz.getMethod("sort", int[].class).invoke(clazz, nearlySoted ? generalNearlySortedIntegers(num) : generalRandomIntegers(num));
            System.out.println(watch.end() + "ms");
            if (!isSorted(sorted)) {
                throw new IllegalStateException("not sorted");
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void testSortAndGetTime() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String callerName = trace[trace.length - 1].getClassName();
        try {
            Class clazz = Class.forName(callerName);
            if (!SortArray.class.isAssignableFrom(clazz)) {
                System.out.println("调用者必须实现" + SortArray.class.getSimpleName() + "接口");
                return;
            }
            int[] data = generalRandomIntegers(1000);
            start();
            int[] result = (int[]) clazz.getMethod("sort", int[].class).invoke(clazz, data);
            long time = end();
            System.out.println((isSorted(result) ? "有序" : "无序") + ",耗时" + time + "ms(包含反射时间)");
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected static void start() {
        if (START != 0) {
            throw new IllegalStateException("double call!");
        }
        START = System.currentTimeMillis();
    }

    protected static long end() {
        long result = System.currentTimeMillis() - START;
        START = 0;
        return result;
    }

    public static void printArray(int[] data) {
        for (int i : data) {
            System.out.printf("%d,", i);
        }
        System.out.println();
    }

    public static boolean less(int value, int other) {
        return value < other;
    }

    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public static boolean greater(int value, int other) {
        return value > other;
    }

    public static boolean equal(int value, int other) {
        return value == other;
    }
}
