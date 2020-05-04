package com.mairuis.algorithm.sort;

/**
 * 计数排序
 * 稳定的排序算法(不破坏相对顺序)
 *
 * @author Mairuis
 * @date 2019/5/25
 */
public class CountingSort extends Sort {

    public static int[] countingSort(int[] a) {
        int[] c = new int[a.length];
        //用无限循环模拟goto，实现超容就扩充数组然后从头开始
        for (; ; ) {
            //统计数组a中每个元素的数量
            for (int anA : a) {
                //数组扩容
                if (c.length <= anA) {
                    c = new int[c.length << 2];
                    break;
                }
                c[anA] += 1;
            }
            break;
        }
        //将c[i]赋值为小于等于i的元素个数
        for (int i = 1; i < c.length; i += 1) {
            c[i] += c[i - 1];
        }
        int[] b = new int[a.length];
        for (int anA : a) {
            b[c[anA] - 1] = anA;
            c[anA] -= 1;
        }
        return b;
    }

    public static int[] sort(int[] data) {
        return countingSort(data);
    }

    public static void main(String[] args) {
        testSort();
    }
}
