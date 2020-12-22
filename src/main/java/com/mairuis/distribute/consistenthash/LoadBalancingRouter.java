package com.mairuis.distribute.consistenthash;

/**
 * @author Mairuis
 * @since 2020/12/20
 */
public interface LoadBalancingRouter<T extends Node> extends Iterable<T> {

    T put(T node);

    T remove(String id);

    T route(String key);

}
