package com.mairuis.algorithm.tree;

import com.mairuis.algorithm.sort.Sort;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 红黑树
 * <p>
 * 基于2-3树思想，保证了局部平衡(从根到任意节点的黑节点数量永远相等)
 *
 * @author Mairuis
 * @date 2019/5/10
 */
public class RedBlackTree<K extends Comparable<K>, V> implements Table<K, V> {
    private static final boolean RED = true, BLACK = false;
    static int searchTime = 0;
    private Node<K, V> root;

    private static <K, V> Node<K, V> leftOf(Node<K, V> p) {
        return (p == null) ? null : p.left;
    }

    private static <K, V> Node<K, V> rightOf(Node<K, V> p) {
        return (p == null) ? null : p.right;
    }

    private static <K, V> boolean colorOf(Node<K, V> p) {
        return (p == null ? BLACK : p.color);
    }

    private static <K, V> Node<K, V> parentOf(Node<K, V> p) {
        return (p == null ? null : p.parent);
    }

    private static <K, V> void setColor(Node<K, V> p, boolean c) {
        if (p != null) {
            p.color = c;
        }
    }

    public static void main(String[] args) {
        RedBlackTree<Integer, Integer> tree = new RedBlackTree<>();
        int[] ints = Sort.generalRandomIntegers(1 << 25);
        for (int i = 0; i < ints.length; i++) {
            tree.put(ints[i], ints[i]);
        }
        for (int i : tree.sort()) {
            System.out.print(i + ",");
            tree.get(i);
        }
        System.out.println(searchTime);
    }

    @Override
    public void put(K key, V val) {
        Node<K, V> tree = root;
        if (tree == null) {
            root = new Node<>(key, val, BLACK, null);
            return;
        }
        for (; ; ) {
            int compare = tree.k.compareTo(key);
            if (compare < 0) {
                if (tree.right == null) {
                    tree.right = new Node<>(key, val, RED, tree);
                    this.fixTree(tree.right);
                    break;
                } else {
                    tree = tree.right;
                }
            } else if (compare > 0) {
                if (tree.left == null) {
                    tree.left = new Node<>(key, val, RED, tree);
                    this.fixTree(tree.left);
                    break;
                } else {
                    tree = tree.left;
                }
            } else {
                tree.v = val;
                tree.k = key;
                break;
            }
        }
    }

    private void fixTree(Node<K, V> x) {
        while (colorOf(parentOf(x)) == RED) {
            //节点是左子树的情况
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                //情况1
                if (colorOf(rightOf(parentOf(parentOf(x)))) == RED) {
                    setColor(rightOf(parentOf(parentOf(x))), BLACK);
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    //情况2
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    //情况3
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                //情况1
                if (colorOf(leftOf(parentOf(parentOf(x)))) == RED) {
                    setColor(leftOf(parentOf(parentOf(x))), BLACK);
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    //情况2
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    //情况3
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    @Override
    public V get(K key) {
        Node<K, V> tree = root;
        int i = 0;
        while (tree != null) {
            if (tree.color == BLACK) {
                i++;
            }
            int compare = tree.k.compareTo(key);
            if (compare < 0) {
                tree = tree.right;
            } else if (compare > 0) {
                tree = tree.left;
            } else {
                if (i > searchTime) {
                    searchTime = i;
                }
                System.out.println("查找了 " + i + " 次");
                return tree.v;
            }
        }
        return null;
    }

    @Override
    public void delete(K key) {

    }

    @Override
    public int size() {
        return 0;
    }

    private void rotateLeft(Node<K, V> x) {
        Node<K, V> y = x.right;
        x.right = y.left;
        if (x.right != null) {
            x.right.parent = x;
        }
        Node<K, V> parent = x.parent;
        y.parent = parent;
        if (parent == null) {
            root = y;
        } else if (x == parent.left) {
            parent.setLeft(y);
        } else if (x == parent.right) {
            parent.setRight(y);
        } else {
            throw new IllegalStateException("left rotate logic error");
        }
        y.left = x;
        x.parent = y;
    }

    public Queue<V> sort() {
        Stack<Node<K, V>> stack = new Stack<>();
        Queue<V> keySet = new LinkedList<>();
        Node<K, V> node = root;
        while (node != null || !stack.isEmpty()) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                keySet.offer(node.v);
                node = node.right;
            }
        }
        return keySet;
    }

    private void rotateRight(Node<K, V> x) {
        Node<K, V> y = x.left;
        x.left = y.right;
        if (x.left != null) {
            x.left.parent = x;
        }
        Node<K, V> parent = x.parent;
        y.parent = parent;
        if (parent == null) {
            root = y;
        } else if (x == parent.right) {
            parent.setRight(y);
        } else if (x == parent.left) {
            parent.setLeft(y);
        } else {
            throw new IllegalStateException("right rotate logic error");
        }
        y.right = x;
        x.parent = y;
    }

    private static class Node<K, V> {
        K k;
        V v;
        Node<K, V> parent, right, left;
        /**
         * 颜色默认为 false 即黑色
         */
        boolean color;

        public Node(K k, V v, boolean color, Node<K, V> parent) {
            this.k = k;
            this.v = v;
            this.color = color;
            this.parent = parent;
        }

        public void setLeft(Node<K, V> left) {
            this.left = left;
        }


        public void setRight(Node<K, V> right) {
            this.right = right;
        }

        public void detach(Node<K, V> node) {
            if (node == right) {
                right = null;
            } else if (node == left) {
                left = null;
            } else {
                throw new IllegalStateException();
            }
        }
    }
}
