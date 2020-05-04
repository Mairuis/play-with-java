package com.mairuis.algorithm.sort;

/**
 * 像是从牌堆中一张一张拿出来和手牌比较然后收入手牌一样的排序算法
 *
 * @author Mairuis
 * @date 2019/1/11
 */
public class InsertSort extends Sort {

    public static int[] insertSort(int[] data) {
        return insertSort(data, 0, data.length);
    }

    public static int[] insertSort(int[] data, int start, int length) {
        //初始化: 从第二个元素开始
        for (int i = start; i < length; i++) {
            //不变式:
            //取出元素与前一个比较
            //这一步类似于手中已经有一张牌后从牌堆中抓一张与手中卡牌比较
            int key = data[i];
            int j = i - 1;
            while (j >= 0 && less(data[j], key)) {
                //将卡牌不断向前推进,类似将这张卡牌向前一张一张挪动
                data[j + 1] = data[j];
                j = j - 1;
            }
            //终止:
            //此时卡牌的比较工作已经完成，当前下标就是卡牌排序后的位置
            data[j + 1] = key;
        }
        return data;
    }

    public static int[] sort(int[] data) {
        return insertSort(data);
    }

    public static void main(String[] args) {
        testSort(10000, true);
    }
}
