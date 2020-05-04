package com.mairuis.algorithm.gather;


import com.mairuis.algorithm.sort.Sort;

/**
 * 支持 O(1) 复杂度查看当前栈中最小元素的栈
 *
 * @author Mairuis
 * @date 2019/5/16
 */
public class MinMStack<V extends Comparable<V>> {

    private java.util.Stack<V> dataStack, minStack;

    public MinMStack() {
        this.dataStack = new java.util.Stack<>();
        this.minStack = new java.util.Stack<>();
    }

    public static void main(String[] args) {
        MinMStack<Integer> minStack = new MinMStack<>();

        int[] ints = Sort.generalRandomIntegers(90);
        for (int i : ints) {
            minStack.push(i);
        }
        while (!minStack.isEmpty()) {
            System.out.println(minStack.min());
            minStack.pop();
        }
    }

    public V pop() {
        V pop = dataStack.pop();
        V min = min();
        if (this.greaterOrEqual(min, pop)) {
            minStack.pop();
        }
        return pop;
    }

    public void push(V v) {
        V min = min();
        dataStack.push(v);
        if (min == null || this.greaterOrEqual(min, v)) {
            minStack.push(v);
        }
    }

    public boolean isEmpty() {
        return dataStack.isEmpty();
    }

    private boolean greaterOrEqual(V a, V b) {
        return a != null && b == null || a == null && b == null || a != null && a.compareTo(b) >= 0;
    }

    public V min() {
        return minStack.isEmpty() ? null : minStack.peek();
    }
}
