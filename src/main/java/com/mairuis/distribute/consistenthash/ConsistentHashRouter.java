package com.mairuis.distribute.consistenthash;

import com.mairuis.algorithm.hash.HashFunc;
import com.mairuis.algorithm.hash.MurmurHash3;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public class ConsistentHashRouter<T extends Node> implements LoadBalancingRouter<T> {
    private final int virtualNode;
    private final HashFunc<String> hashFunc;
    private final NavigableMap<Integer, T> ring;

    public ConsistentHashRouter() {
        this(new MurmurHash3(), 20);
    }

    public ConsistentHashRouter(HashFunc<String> hashFunc, int virtualNode) {
        this.hashFunc = hashFunc;
        this.virtualNode = virtualNode;
        this.ring = new ConcurrentSkipListMap<>();
    }

    @Override
    public T put(T node) {
        return this.ring.put(hashFunc.hash(node.getUniqueId()), node);
    }

    @Override
    public T remove(String id) {
        return this.ring.remove(hashFunc.hash(id));
    }

    @Override
    public T route(String id) {
        Integer key = this.ring.ceilingKey(hashFunc.hash(id));
        if (key == null) {
            key = this.ring.firstKey();
        }
        return this.ring.get(key);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.ring.values().iterator();
    }
}
