package com.mairuis.algorithm.sort;

/**
 * 选择排序
 *
 * @author Mairuis
 * @date 2019/3/28
 */
public class SelectionSort extends Sort {


    public static int[] sort(int[] A) {
        for (int i = 0; i < A.length; i += 1) {
            int min = getMin(A, i, A.length);
            //swap min and cur elements
            int temp = A[i];
            A[i] = A[min];
            A[min] = temp;
        }
        return A;
    }

    public static int getMin(int[] A, int start, int end) {
        int min = start;
        for (int i = start + 1; i < end; i += 1) {
            if (A[i] < A[min]) {
                min = i;
            }
        }
        return min;
    }

    public static void main(String[] args) {
        printArray(generalNearlySortedIntegers(100));
    }
}
