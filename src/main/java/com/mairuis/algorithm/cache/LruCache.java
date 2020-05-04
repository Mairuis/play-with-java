package com.mairuis.algorithm.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * LRU Map
 * HashMap和双向链表实现
 * 淘汰最近最少使用
 *
 * @author Mairuis
 * @date 2019/6/13
 */
public class LruCache {

    /**
     * 双向链表实现的队列
     * 这个队列只有两种状态，头结点和尾结点都为空或头结点和尾结点都不为空
     * 当队列不为空时头结点的prev一定为空，而为节点的next也一定为空
     */
    private Node head, tail;
    private Map<Integer, Node> table;
    private int capacity;

    public LruCache(int capacity) {
        this.capacity = capacity;
        this.head = tail = null;
        this.table = new HashMap<>(capacity);
    }

    public int get(int key) {
        Node node = table.get(key);
        if (node == null) {
            return -1;
        }
        this.swim(node, node);
        return node.VALUE;
    }

    public void put(int key, int value) {
        Node newValue = new Node(key, value);
        Node oldValue = table.put(key, newValue);
        //这里有三种情况
        //1.key是新的
        //2.key已经存在,但value不一样
        //3.key已经存在且value也相等
        //对于第一种情况，我们把键值对存进table并把key入队同时做一次裁剪，其余情况把key移动到队尾
        if (oldValue == null) {
            this.enqueue(newValue);
            this.trim();
        } else {
            this.swim(oldValue, newValue);
        }
    }

    /**
     * 删除一个元素从队列
     *
     * @param node
     */
    private Node detach(Node node) {
        if (head == node) {
            if (head == tail) {
                head = tail = null;
            } else {
                //头节点
                head = head.next;
            }
        } else if (tail == node) {
            //尾节点
            tail = tail.prev;
            tail.next = null;
        } else {
            //中间节点
            link(node.prev, node.next);
        }
        return node;
    }

    /**
     * 连接两个节点
     *
     * @param head
     * @param tail
     */
    private void link(Node head, Node tail) {
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 将节点入队
     *
     * @param node
     */
    private void enqueue(Node node) {
        if (tail == null) {
            head = tail = node;
        } else {
            this.link(tail, node);
            this.tail = node;
        }
    }

    /**
     * 头结点出队
     *
     * @return
     */
    private Node pollFirst() {
        return this.detach(head);
    }

    private void swim(Node oldNode, Node newNode) {
        this.detach(oldNode);
        this.enqueue(newNode);
    }

    private void trim() {
        if (table.size() > capacity) {
            //将头结点出队
            Node head = this.pollFirst();
            //删除头结点
            table.remove(head.KEY);
        }
    }

    static class Node {
        final int VALUE;
        final int KEY;
        Node next, prev;

        Node(final int key, final int value) {
            this.VALUE = value;
            this.KEY = key;
        }
    }
}