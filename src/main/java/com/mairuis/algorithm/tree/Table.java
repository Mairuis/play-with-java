package com.mairuis.algorithm.tree;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/5/6
 */
public interface Table<K extends Comparable<K>, V> {

    void put(K key, V val);

    V get(K key);

    void delete(K key);

    default boolean contains(K key) {
        return get(key) != null;
    }

    default boolean isEmpty() {
        return size() == 0;
    }

    int size();

    default K min() {
        return null;
    }

    default K max() {
        return null;
    }

    default K floor(K key) {
        return null;
    }

    default K ceil(K key) {
        return null;
    }

    default int rank(K key) {
        return 0;
    }

    default K select(int rank) {
        return null;
    }

    default K getMinKey() {
        return null;
    }

    default K getMaxKey() {
        return null;
    }

    default int size(K lo, K hi) {
        return 0;
    }

    default Iterable<K> keys(K lo, K hi) {
        return null;
    }

    default Iterable<K> keys() {
        return null;
    }
}
