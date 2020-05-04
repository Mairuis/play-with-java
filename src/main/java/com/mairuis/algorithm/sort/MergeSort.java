package com.mairuis.algorithm.sort;

/**
 * 归并排序
 *
 * @author Mairuis
 * @date 2019/1/25
 */
public class MergeSort extends Sort {

    public static final int CUTOFF = 4;

    public static int[] mergeSort(int[] data, int l, int r) {
        if (r > l) {
            int c = l + ((r - l) >> 1);
            mergeSort(data, l, c);
            mergeSort(data, c + 1, r);
            merge(data, l, c, r);
        }
        return data;
    }

    public static int[] merge(int[] data, int l, int c, int r) {
        int[] data2 = new int[data.length];
        //真的太烦了 我直接拷贝整个数组!!!
        System.arraycopy(data, 0, data2, 0, data2.length);
        int lCur = l, rCur = c + 1;
        for (int i = l; i <= r; i++) {
            if (lCur > c) {
                data[i] = data2[rCur++];
            } else if (rCur > r) {
                data[i] = data2[lCur++];
            } else if (data2[lCur] >= data2[rCur]) {
                data[i] = data2[lCur++];
            } else {
                data[i] = data2[rCur++];
            }
        }
        return data;
    }


    public static int[] sort(int[] data) {
        return mergeSort(data, 0, data.length - 1);
    }

    public static void main(String[] args) {
        testSort();
    }
}

