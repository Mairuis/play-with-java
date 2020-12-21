package com.mairuis.distribute.consistenthash;

import com.mairuis.algorithm.hash.HashFunc;
import com.mairuis.algorithm.hash.MurmurHash3;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public class ConsistentHashRouter implements LoadBalancingRouter {

    private final HashFunc<String> hashFunc;

    public ConsistentHashRouter() {
        this(new MurmurHash3());
    }

    public ConsistentHashRouter(HashFunc<String> hashFunc) {
        this.hashFunc = hashFunc;
    }

    @Override
    public Node put(Node node) {
        return null;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Node route(String key) {
        return null;
    }
}
