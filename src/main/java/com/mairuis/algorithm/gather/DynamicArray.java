package com.mairuis.algorithm.gather;

import java.util.Iterator;

/**
 * 动态数组
 * 防复杂度震荡，平摊分析后复杂度为O(1)
 *
 * @author Mairuis
 * @date 2019/6/30
 */
public class DynamicArray<V> implements Iterable<V> {
    private Object[] array;
    private int size;

    public DynamicArray(int initCapacity) {
        this.array = new Object[initCapacity];
        this.size = 0;
    }

    public static void main(String[] args) {
        DynamicArray<Integer> d = new DynamicArray<>(1);
        d.add(0);
        d.add(1);
        d.add(2);
        d.add(3);
        d.add(4);
        d.add(5);
        d.remove(4);

        for (int i : d) {
            System.out.println(i);
        }
    }

    /**
     * 添加一个元素并返回该元素在数组中的下标
     *
     * @param v
     * @return
     */
    public int add(V v) {
        if (size >= array.length) {
            this.resize(array.length << 1);
        }
        this.array[size] = v;
        this.size += 1;
        return this.size - 1;
    }

    public void remove(int index) {
        //check range
        if (index < 0 && index >= size) {
            throw new IllegalArgumentException();
        }
        final int newSize;
        if ((newSize = size - 1) > index)
            System.arraycopy(array, index + 1, array, index, newSize - index);
        this.array[newSize] = null;
        this.size = newSize;

        //array resize
        int capacity = array.length;
        if (size <= capacity / 4) {
            int newCap = capacity / 2;
            this.resize(newCap);
        }
    }

    private void resize(int newCap) {
        Object[] newArray = new Object[newCap];
        int copySize = newCap > size ? size : newCap;
        System.arraycopy(array, 0, newArray, 0, copySize);
        this.array = newArray;
    }

    public void swap(int a, int b) {
        Object temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<V> iterator() {
        return new IteratorImpl<>(this);
    }

    /**
     * 获取数组中最后一个元素的下标
     *
     * @return
     */
    public int getLastIndex() {
        return size > 0 ? size - 1 : 0;
    }

    public void set(int index, V value) {
        if (index > this.size || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.array[index] = value;
    }

    public V get(int cur) {
        return (V) this.array[cur];
    }

    private class IteratorImpl<V> implements Iterator<V> {

        private DynamicArray<V> obj;
        private int cur;

        IteratorImpl(DynamicArray<V> obj) {
            this.obj = obj;
            this.cur = 0;
        }

        @Override
        public boolean hasNext() {
            return obj.size() > cur;
        }

        @Override
        public V next() {
            V v = obj.get(cur);
            cur += 1;
            return v;
        }

        @Override
        public void remove() {
            obj.remove(cur);
        }

    }
}
