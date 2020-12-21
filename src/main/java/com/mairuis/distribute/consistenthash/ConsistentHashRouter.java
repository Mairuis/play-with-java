package com.mairuis.distribute.consistenthash;

import com.mairuis.algorithm.hash.HashFunc;
import com.mairuis.algorithm.hash.MurmurHash3;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public class ConsistentHashRouter implements LoadBalancingRouter {

    private final HashFunc<String> hashFunc;
    private final ConcurrentNavigableMap<Integer, Node> hashRing;

    public ConsistentHashRouter() {
        this(new MurmurHash3());
    }

    public ConsistentHashRouter(HashFunc<String> hashFunc) {
        this.hashFunc = hashFunc;
        this.hashRing = new ConcurrentSkipListMap<>();
    }

    @Override
    public Node put(Node node) {
        return this.hashRing.put(hashFunc.hash(node.getUniqueId()), node);
    }

    @Override
    public Node remove(String id) {
        return this.hashRing.remove(hashFunc.hash(id));
    }

    @Override
    public Node route(String key) {
        Integer floorKey = this.hashRing.floorKey(hashFunc.hash(key));
        return this.hashRing.get(floorKey);
    }
}
