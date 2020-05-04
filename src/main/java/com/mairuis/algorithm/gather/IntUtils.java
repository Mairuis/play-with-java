package com.mairuis.algorithm.gather;

/**
 * 整数相关算法
 *
 * @author Mairuis
 * @date 2019/7/3
 */
public class IntUtils {

    public static int findNotTwoRepeat(int[] numbers) {
        int temp = 0;
        for (int i : numbers) {
            temp ^= i;
        }
        return temp;
    }

    public static long pow(long x, int n) {
        if (n <= 1) {
            return x;
        }
        return n % 2 == 0 ? pow(x * x, n / 2) : x * pow(x * x, n / 2);
    }

    /**
     * 利用2的幂的数字?都是 10,100,1000 的性质确定传入数i是否是2的幂
     *
     * @param i
     * @return
     */
    public static boolean isTwoPower(int i) {
        return i > 0 && (i & (i - 1)) == 0;
    }

    public static int binarySearch(int[] a, int cur) {
        //[0,a.length - 1]区间内查找
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >>> 2);
            if (a[mid] > cur) {
                right = mid - 1;
            } else if (a[mid] < cur) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

    }

}
