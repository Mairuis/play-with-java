package com.mairuis.distribute.consistenthash;

import com.mairuis.algorithm.hash.HashFunc;
import com.mairuis.algorithm.hash.MurmurHash3;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * tend to trade space for time consistent hash implements.
 * fault tolerance for hash collision.
 * <p>
 * https://web.stanford.edu/class/cs168/l/l1.pdf
 *
 * @author Mairuis
 * @since 2020/12/20
 */
public class ConsistentHashRouter<T extends Node> implements LoadBalancingRouter<T> {
    private final int virtualNodeAmount;
    private final HashFunc<String> hashFunc;
    private final NavigableMap<Integer, RingNode<T>> ring;
    private final Map<Integer, RealNode<T>> realNodes;
    private final Map<Integer, List<VirtualNode<T>>> virtualNodes;

    public ConsistentHashRouter() {
        this(32);
    }

    public ConsistentHashRouter(int virtualNode) {
        this(virtualNode, new MurmurHash3());
    }

    public ConsistentHashRouter(int virtualNode, HashFunc<String> hashFunc) {
        this.hashFunc = hashFunc;
        this.virtualNodeAmount = virtualNode;
        this.ring = new ConcurrentSkipListMap<>();
        this.virtualNodes = new ConcurrentHashMap<>();
        this.realNodes = new ConcurrentHashMap<>();
    }

    @Override
    public T put(T node) {
        RealNode<T> realNode = new RealNode<>(node);
        int hash = hashFunc.hash(realNode.getId());
        RingNode<T> previousNode = this.ring.put(hash, realNode);
        if (virtualNodeAmount != 0) {
            for (int virtualId = 0; virtualId < virtualNodeAmount; virtualId++) {
                VirtualNode<T> virtualNode = new VirtualNode<>(node, virtualId, hashFunc);
                //if hash collision, the virtual node will replace collision node, it's ok
                this.ring.put(virtualNode.getHashCode(), virtualNode);
                this.virtualNodes.computeIfAbsent(hash, (k) -> new CopyOnWriteArrayList<>()).add(virtualNode);
            }
        }
        this.realNodes.put(hash, realNode);
        return previousNode == null ? null : previousNode.getNode();
    }


    @Override
    public T remove(String id) {
        int hash = hashFunc.hash(id);
        RingNode<T> removed = this.ring.remove(hash);
        if (virtualNodeAmount > 0 && removed != null) {
            List<VirtualNode<T>> virtualNodes = this.virtualNodes.get(hash);
            if (virtualNodes != null) {
                for (VirtualNode<T> virtualNode : virtualNodes) {
                    RingNode<T> removedVirtualNode = this.ring.remove(virtualNode.getHashCode());
                    if (removedVirtualNode.getNode() != removed.getNode()) {
                        //in this case, we have a hash collision by a virtual node
                        //we need to put this node back
                        this.ring.put(hashFunc.hash(removedVirtualNode.getId()), removedVirtualNode);
                    }
                }
            }
        }
        //for hash collision, must to do this
        this.realNodes.remove(hash);
        return removed == null ? null : removed.getNode();
    }

    @Override
    public T route(String id) {
        Integer key = this.ring.ceilingKey(hashFunc.hash(id));
        if (key == null) {
            key = this.ring.firstKey();
        }
        RingNode<T> node = this.ring.get(key);
        return node == null ? null : node.getNode();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return this.realNodes.values().stream().map(RingNode::getNode).iterator();
    }

    interface RingNode<T extends Node> {

        String getId();

        T getNode();
    }

    static class RealNode<T extends Node> implements RingNode<T> {

        private final T node;

        RealNode(T node) {
            this.node = node;
        }

        @Override
        public String getId() {
            return node.getUniqueId();
        }

        @Override
        public T getNode() {
            return node;
        }
    }

    static class VirtualNode<T extends Node> implements RingNode<T> {

        private final T realNode;
        private final String virtualId;
        private final int hashCode;

        public VirtualNode(T realNode, int virtualId, HashFunc<String> hashFunc) {
            this.realNode = realNode;
            this.virtualId = virtualNodeNamePattern(realNode.getUniqueId(), virtualId);
            this.hashCode = hashFunc.hash(this.virtualId);
        }

        private String virtualNodeNamePattern(String uniqueId, int virtualId) {
            return uniqueId + "->" + virtualId;
        }

        @Override
        public T getNode() {
            return realNode;
        }

        @Override
        public String getId() {
            return virtualId;
        }

        public int getHashCode() {
            return hashCode;
        }
    }
}
