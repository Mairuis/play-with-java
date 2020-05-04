package com.mairuis.algorithm.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二叉搜索树
 *
 * @author Mairuis
 * @date 2019/5/6
 */
public class BinarySearchTree<K extends Comparable<K>, V> implements Table<K, V> {

    private Node<K, V> root;

    public static void main(String[] args) {
        BinarySearchTree<Integer, String> table = new BinarySearchTree<>();
        table.put(2, "2");
        table.put(1, "1");
        table.put(3, "3");
        table.put(4, "4");
        table.put(5, "5");
        table.put(6, "6");


//        System.out.println(table.successor(2).k);
//        System.out.println(table.predecessor(2).k);

//        for (int i : table.keys()) {
//            System.out.println(i + ": " + table.get(i));
//        }


//        System.out.println(table.get(table.getMaxKey()));
//        System.out.println(table.get(table.getMinKey()));
//        System.out.println(table.floor(5));
//        System.out.println(table.ceil(5));
//
//        System.out.println(table.select(table.rank(8)) == 8);
//        System.out.println(table.select(table.rank(3)) == 3);
//        System.out.println(table.select(table.rank(4)) == 4);
//        System.out.println(table.select(table.rank(1)) == 1);
//        table.printSort();
//
//        for (int s : table.keys()) {
//            System.out.println(s + ": " + table.get(s));
//        }
//        System.out.println(99 + ": " + table.get(99));
//
//        System.out.println(table.min());
//        System.out.println(table.max());
    }

    @Override
    public void put(K k, V v) {
        if (root == null) {
            root = new Node<>(k, v, 1);
            return;
        }
        Node<K, V> node = root;
        final byte insert = 0x1;
        final byte isNew = 0x2;
        byte sign = 0;
        while ((sign & insert) == 0) {
            if (node.compareTo(k) < 0) {
                if (node.right == null) {
                    node.right = new Node<>(k, v, node, 1);
                    node = node.right;
                    sign = insert | isNew;
                } else {
                    node = node.right;
                }
            } else if (node.compareTo(k) > 0) {
                if (node.left == null) {
                    node.left = new Node<>(k, v, node, 1);
                    node = node.left;
                    sign = insert | isNew;
                } else {
                    node = node.left;
                }
            } else {
                node.v = v;
                sign = insert;
            }
        }
        if ((sign & isNew) != 0) {
            while ((node = node.parent) != null) {
                node.degree += 1;
            }
        }
    }

    private int sizeof(Node node) {
        return node == null ? 0 : node.degree;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = root;
        while (node != null) {
            if (node.compareTo(key) < 0) {
                node = node.right;
            } else if (node.compareTo(key) > 0) {
                node = node.left;
            } else {
                return node.v;
            }
        }
        return null;
    }

    public Node<K, V> getNode(K key) {
        Node<K, V> node = root;
        while (node != null) {
            if (node.compareTo(key) < 0) {
                node = node.right;
            } else if (node.compareTo(key) > 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {
        Node<K, V> node = root;
        while (node != null) {
            if (node.compareTo(key) < 0) {
                node = node.right;
            } else if (node.compareTo(key) > 0) {
                node = node.left;
            } else {
                this.delete(node);
                break;
            }
        }
    }

    /**
     * 删除一个节点
     *
     * @param node
     */
    private void delete(Node<K, V> node) {
        Node<K, V> left = node.left;
        Node<K, V> parent = node.parent;
        Node<K, V> right = node.right;
        //这里一共四种情况
        //1.左右都是空的
        if (left == null && right == null) {
            if (parent == null) {
                root = null;
            } else {
                parent.update(node, null);
            }
        } else {
            //2.左节点是空的
            if (left == null) {
                if (parent == null) {
                    root = right;
                    root.parent = null;
                } else {
                    parent.update(node, right);
                }
            } else if (right == null) {
                //3.右节点是空的
                if (parent == null) {
                    root = left;
                    root.parent = null;
                } else {
                    parent.update(node, left);
                }
            } else {
                //4.左右都不是空的
                while (right.left != null) {
                    right = right.left;
                }
                parent.update(node, right);
                left.parent = right;
            }
        }
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return sizeof(root);
    }

    @Override
    public K min() {
        Node<K, V> node = root;
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node.k;
    }

    @Override
    public K max() {
        Node<K, V> node = root;
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node.k;
    }

    @Override
    public K floor(K key) {
        Node<K, V> node = root;
        Node<K, V> last = null;
        while (node != null) {
            int result = node.compareTo(key);
            //node > key
            if (result > 0) {
                node = node.left;
            } else if (result < 0) {
                //node < key
                last = node;
                node = node.right;
            } else {
                //node == key
                return node.k;
            }
        }
        return last == null ? null : last.k;
    }

    @Override
    public K ceil(K key) {
        Node<K, V> node = root;
        Node<K, V> last = null;
        while (node != null) {
            int result = node.compareTo(key);
            //node > key
            if (result > 0) {
                last = node;
                node = node.left;
            } else if (result < 0) {
                //node < key
                node = node.right;
            } else {
                //node == key
                return node.k;
            }
        }
        return last == null ? null : last.k;
    }

    @Override
    public int rank(K key) {
        return rank(key, root);
    }

    public int rank(K key, Node<K, V> node) {
        if (node == null) {
            return 0;
        }
        int result = key.compareTo(node.k);
        if (result < 0) {
            return rank(key, node.left);
        } else if (result > 0) {
            return 1 + rank(key, node.left) + rank(key, node.right);
        } else {
            return sizeof(node.left);
        }
    }

    @Override
    public K select(int rank) {
        return select(rank, root).k;
    }

    @Override
    public K getMinKey() {
        Node<K, V> node = root;
        while (node != null && node.left != null) {
            node = node.left;
        }
        return node == null ? null : node.k;
    }

    @Override
    public K getMaxKey() {
        Node<K, V> node = root;
        while (node != null && node.right != null) {
            node = node.right;
        }
        return node == null ? null : node.k;
    }

    /**
     * 后继节点
     *
     * @param k
     */
    public Node<K, V> successor(K k) {
        Node<K, V> keyNode = getNode(k);
        Node<K, V> successor = null;
        Node<K, V> node = keyNode == null ? null : keyNode.right;
        while (node != null) {
            successor = node;
            node = node.left;
        }
        return successor;
    }

    /**
     * 前驱节点
     *
     * @param k
     */
    public Node<K, V> predecessor(K k) {
        Node<K, V> keyNode = getNode(k);
        Node<K, V> predecessor = null;
        Node<K, V> node = keyNode == null ? null : keyNode.left;
        while (node != null) {
            predecessor = node;
            node = node.right;
        }
        return predecessor;
    }

    public Node<K, V> select(int rank, Node<K, V> node) {
        if (node == null) {
            return null;
        }
        //node > key
        int leftSize = sizeof(node.left);
        if (leftSize > rank) {
            return select(rank, node.left);
        } else if (leftSize < rank) {
            //node < key
            return select(rank - leftSize - 1, node.right);
        } else {
            //node == key
            return node;
        }
    }

    @Override
    public int size(K lo, K hi) {
        return root == null ? 0 : root.degree;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        Stack<Node<K, V>> stack = new Stack<>();
        Queue<K> keySet = new LinkedList<>();
        Node<K, V> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                if (node.compareTo(lo) >= 0 && node.compareTo(hi) <= 0) {
                    keySet.offer(node.k);
                }
                node = node.right;
            }
        }
        return keySet;
    }

    @Override
    public Iterable<K> keys() {
        Stack<Node<K, V>> stack = new Stack<>();
        Queue<K> keySet = new LinkedList<>();
        Node<K, V> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                keySet.offer(node.k);
                node = node.right;
            }
        }
        return keySet;
    }

    public Queue<K> keysLayer() {
        Queue<Node<K, V>> queue = new LinkedList<>();
        Queue<K> keySet = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<K, V> node = queue.poll();
            keySet.offer(node.k);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return keySet;
    }

    public static class Node<K extends Comparable<K>, V> implements Comparable<K> {

        public K k;
        public V v;
        public int degree;

        public Node<K, V> parent;
        public Node<K, V> right;
        public Node<K, V> left;

        public Node(K k, V v, int degree) {
            this.k = k;
            this.v = v;
            this.degree = degree;
        }

        public Node(K k, V v, Node<K, V> parent, int degree) {
            this.k = k;
            this.v = v;
            this.degree = degree;
            this.parent = parent;
        }

        public void update(Node<K, V> node, Node<K, V> newNode) {
            if (left == node) {
                left = newNode;
            } else if (right == node) {
                right = newNode;
            } else {
                throw new IllegalStateException();
            }
            if (newNode != null) {
                newNode.parent = this;
            }
        }

        @Override
        public int compareTo(K o) {
            return k.compareTo(o);
        }
    }
}
