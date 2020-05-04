package com.mairuis.algorithm.leetcode.binaryheap;

import com.mairuis.algorithm.analysis.Watch;
import com.mairuis.algorithm.sort.Sort;

import java.util.PriorityQueue;

/**
 * 在N的元素中取排名前M的元素的问题
 *
 * @author Mairuis
 * @date 2019/6/30
 */
public class FrontM {

    public static PriorityQueue<Integer> prev(int[] nums, int m) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : nums) {
            if (pq.size() < m) {
                pq.add(i);
            } else {
                if (pq.peek() < i) {
                    pq.poll();
                    pq.add(i);
                }
            }
        }
        return pq;
    }

    public static void main(String[] args) {
        Watch tw = new Watch();
        tw.begin();
        Iterable<Integer> r = prev(Sort.generalNearlySortedIntegers(100000), 1000);
        float v = tw.end();
        for (int i : r) System.out.println(i);
        System.out.println(v + "ms");
    }
}
