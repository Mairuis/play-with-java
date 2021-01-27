package com.mairuis.distribute.loadbalancing;

/**
 * the node at practice indicates a machine in quorum, or application instance.
 *
 * @author Mairuis
 * @since 2020/12/20
 */
public interface Node {

    /**
     * get the unique id of this node
     *
     * @return unique id
     */
    String getUniqueId();

    /**
     * test it is available
     *
     * @return return true if available
     */
    boolean isAvailable();
}
