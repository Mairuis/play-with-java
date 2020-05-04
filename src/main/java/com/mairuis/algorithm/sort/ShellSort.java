package com.mairuis.algorithm.sort;

/**
 * 希尔排序
 * <p>
 * 增量序列必须保证前一个和下一个互质
 *
 * @author Mairuis
 * @date 2019/1/26
 */
public class ShellSort extends Sort {

    public static int[] sort(int[] data) {
        int length = data.length;
        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h >= 1) {
            for (int i = h; i < length; i += h) {
                int right = data[i];
                int left = i - h;

                while (left >= 0 && data[left] > right) {
                    data[left + h] = data[left];
                    left -= h;
                }
                data[left + h] = right;
            }
            h /= 3;
        }
        return data;
    }

    public static void main(String[] args) {
        testSort();
    }
}