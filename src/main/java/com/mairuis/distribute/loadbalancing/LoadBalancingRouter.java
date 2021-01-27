package com.mairuis.distribute.loadbalancing;

/**
 * load-balancing router
 *
 * @author Mairuis
 * @since 2020/12/20
 */
public interface LoadBalancingRouter<T extends Node> extends Iterable<T> {

    /**
     * register node to the router
     *
     * @param node
     * @return the previous value associated with {@code node.getUniqueId}, or null if there was no mapping for {@code node.getUniqueId}
     */
    T put(T node);

    /**
     * remove node from router
     *
     * @param id
     * @return the removed value associated with {@code node.getUniqueId}, or null if there was no mapping for {@code node.getUniqueId}
     */
    T remove(String id);

    /**
     * route by the key
     *
     * @param key
     * @return the target node by routing
     */
    T route(String key);

}
