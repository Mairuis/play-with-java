package com.mairuis.distribute.consistenthash;

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
     * @return
     */
    String getUniqueId();

}
