package com.mairuis.algorithm.sort;

/**
 * 快速排序
 *
 * @author Mairuis
 * @date 2019/5/9
 */
public class QuickSort extends Sort {

    public static int[] quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
        return array;
    }

    public static int partition(int[] array, int p, int r) {
        int x = array[r];
        //[p,i) 代表小于r的区间
        //[i,j)代表大于r的区间
        int i = p - 1;
        for (int j = p; j < r; j++) {
            //如果小于r，则更新小于r的区间
            if (array[j] <= x) {
                swap(array, i += 1, j);
            }
        }
        //最终将r放到中间
        swap(array, i += 1, r);
        return i;
    }

    public static int[] sort(int[] array) {
        return quickSort(array, 0, array.length - 1);
    }


    public static void main(String[] args) {
        testSort(10000, true);

    }
}
