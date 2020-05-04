package com.mairuis.algorithm.tree;

/**
 * 哈希表当前大小为2的幂 设 N
 * 扩容后N * 2,
 * 对于冲突元素 X 的哈希值 H 有 H % N == X
 *
 * @author Mairuis
 * @date 2019/7/17
 */
public class Rehash {


    public static void main(String[] args) {

        int k = 0;
        for (int i = 0; i < Integer.MAX_VALUE; i += 1) {
            if (i % 10 == 6) {
                System.out.println(i);
                k += 1;
            }

            if (k > 10) {
                break;
            }
        }

        Node[] T = new Node[4];
        Node H1 = new Node(1, 2, new Node(2, 6, null));

        T[H1.hash & (T.length - 1)] = H1;

        Node[] T2 = new Node[T.length * 2];
        for (int i = 0; i < T.length; i += 1) {
            Node N = T[i];
            while (N != null) {
                int hash = N.hash;
                int v = N.v;

                if (T2[hash & (T2.length - 1)] == null) {
                    T2[hash & (T2.length - 1)] = new Node(v, hash, null);
                } else {
                    Node n = T2[hash & (T2.length - 1)];
                    while (n.next != null) {
                        n = n.next;
                    }
                    n.next = new Node(v, hash, null);
                }

                N = N.next;
            }
        }
        System.out.println("");
    }

    static class Node {
        Node next;
        int hash;
        int v;

        public Node(int v, int hash, Node next) {
            this.v = v;
            this.hash = hash;
            this.next = next;
        }
    }
}
