package com.mairuis.distribute.consistenthash;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public interface LoadBalancingRouter {

    Node put(Node node);

    Node remove(String id);

    Node route(String key);

}
