package com.mairuis.algorithm.string;

import java.util.*;

/**
 * 普通Trie树
 *
 * @author Mairuis
 * @date 2019/7/1
 */
public class Trie<V> extends AbstractMap<String, V> {
    private final Node<V> root;
    private int size;

    public Trie() {
        this.size = 0;
        this.root = new Node<>(null, null, new HashMap<>());
    }

    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<>();
        trie.put("aca", 1);
        trie.put("aba", 2);
        trie.put("aea", 3);
        trie.put("afa", 4);
        trie.put("ada", 5);
        trie.put("ade", 5);
        trie.put("adeb", 5);

//        System.out.println(trie.get("aba"));
//        System.out.println(trie.remove("aba"));
//        System.out.println(trie.get("aba"));
//        System.out.println(trie.get("ab"));
//        trie.print(trie.root);
        for (String s : trie.keysWithPrefix("ad%")) {
            System.out.println(s);
        }
    }

    @Override
    public V put(String key, V value) {
        int i = 0;
        V oldValue = null;
        Node<V> node = root;
        while (i < key.length()) {
            char cKey = key.charAt(i);
            i += 1;
            Node<V> oldNode = node.next(cKey);
            if (oldNode == null || i >= key.length()) {
                node = node.put(new Node<>(cKey, i >= key.length() ? value : null));
                if (oldNode != null && oldNode.isEndPoint()) {
                    oldValue = oldNode.value;
                }
            } else {
                node = oldNode;
            }
        }
        return oldValue;
    }

    @Override
    public V get(Object key) {
        if (key instanceof String) {
            return get((String) key);
        }
        throw new IllegalArgumentException();
    }

    public V get(String key) {
        Node<V> node = getNode(key);
        return node == null ? null : node.value;
    }

    private Node<V> getNode(String key) {
        int i = 0;
        Node<V> node = root;
        while (node != null && key.length() > i) {
            node = node.next(key.charAt(i));
            i += 1;
        }
        return node;
    }

    @Override
    public V remove(Object key) {
        if (key instanceof String) {
            return remove((String) key);
        }
        throw new IllegalArgumentException();
    }

    public V remove(String key) {
        int i = 0;
        Node<V> end = root, node = root;
        char endKey = key.charAt(0);
        while (node != null && key.length() > i) {
            char charKey = key.charAt(i);
            if (node.next.size() > 1) {
                endKey = charKey;
                end = node;
            }
            node = node.next(charKey);
            i += 1;
        }
        if (node != null) {
            end.remove(endKey);
            return node.value;
        } else {
            return null;
        }
    }

    public List<String> keysWithPrefix(String pattern) {
        //从root节点开始搜索，如果next节点存在则将value相加
        return searchPattern(new ArrayList<>(), pattern, root, 0, "");
    }

    private List<String> searchPattern(List<String> list, String pattern, Node<V> node, int index, String value) {
        if (node == null || index >= pattern.length()) {
            return list;
        }
        char pat = pattern.charAt(index);
        if (pat == '*' || pat == '%') {
            for (Node<V> next : node.next.values()) {
                String nextV = value + next.key;
                if (next.isEndPoint()) {
                    list.add(nextV);
                }
                searchPattern(list, pattern, next, pat == '%' ? index : index + 1, nextV);
            }
        } else {
            Node<V> next = node.next(pat);
            if (next != null) {
                String nextV = value + next.key;
                if (next.isEndPoint()) {
                    list.add(nextV);
                }
                searchPattern(list, pattern, next, index + 1, nextV);
            }
        }
        return list;
    }

    public String longestPrefixOf(String prefix) {
        throw new UnsupportedOperationException();
    }

    public Set<String> keysThatMatch(String pattern) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<Entry<String, V>> entrySet() {
        return null;
    }

    public void print(Node<V> node) {
        System.out.println(node.key);
        System.out.println(node.isEndPoint());
        for (Node<V> n : node.next.values()) {
            print(n);
        }
    }

    private static class Node<V> {
        public Character key;
        public V value;
        public Map<Character, Node<V>> next;

        public Node(Character key, V value) {
            if (key != null) {
                this.key = key;
            }
            this.value = value;
            this.next = new HashMap<>(0, 0.25F);
        }

        public Node(Character key, V value, Map<Character, Node<V>> tree) {
            if (key != null) {
                this.key = key;
            }
            this.value = value;
            this.next = tree;
        }

        public int nextCount() {
            return next.size();
        }

        public Node<V> put(Node<V> node) {
            this.next.put(node.key, node);
            return node;
        }

        public Node<V> next(char key) {
            return next.get(key);
        }

        public void remove(char key) {
            this.next.remove(key);
        }

        public boolean isEndPoint() {
            return value != null;
        }
    }

}
