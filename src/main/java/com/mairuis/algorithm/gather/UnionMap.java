package com.mairuis.algorithm.gather;

/**
 * 动态连通图 判断是否可达
 * 针对不修改节点连接状态
 * <p>
 * 未来设计:
 * 面向不可解除连接:
 * - 增加回溯时展平子节点
 * <p>
 * 面向可解除连接:
 * - 为保证关系性，不展平叶子结点
 * - 为保证权重优化策略有效，解除时需要回溯至根节点
 *
 * @author Mairuis
 * @date 2019/3/28
 */
public class UnionMap {

    /**
     * key = 坐标
     * value = 该节点的父节点
     */
    private int[] map;

    /**
     * 节点树的权重值
     */
    private int[] weight;

    /**
     * 传入一个无重复元素集合初始化这个连通图
     *
     * @param elements
     */
    public UnionMap(int[] elements) {
        if (elements != null && elements.length > 0) {
            this.map = new int[elements.length + 1];
            this.weight = new int[elements.length + 1];
            for (int i : elements) {
                this.map[i] = i;
            }
        } else {
            throw new IllegalStateException("set is cannot be null or empty!");
        }
    }

    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        UnionMap um = new UnionMap(ints);
        um.union(1, 2);
        um.union(3, 2);
        um.union(4, 5);
        System.out.println(um.connected(1, 2) && um.connected(3, 1) && um.connected(4, 5));
        System.out.println(um.dump());
    }

    /**
     * 连通两个节点
     *
     * @param p
     * @param q
     */
    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        int pWeight = weight(pRoot);
        int qWeight = weight(qRoot);
        if (pWeight >= qWeight) {
            map[pRoot] = qRoot;
            weight(pRoot, pWeight + qWeight);
        } else {
            map[qRoot] = pRoot;
            weight(qRoot, pWeight + qWeight);
        }
    }

    private int weight(int p) {
        int i = this.weight[p];
        return i == 0 ? 1 : i;
    }

    private void weight(int p, int v) {
        this.weight[p] = v;
    }

    /**
     * 回溯获取根节点
     *
     * @param p
     * @return
     */
    private int root(int p) {
        int root = p;
        while (map[root] != root) {
            root = map[root];
        }
        //回溯时展平子节点
        map[p] = root;
        return root;
    }

    /**
     * 判断两个节点是否相通
     *
     * @param p
     * @param q
     * @return
     */
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public String dump() {
        StringBuilder dump = new StringBuilder(map.length * 2);
        for (int p = 0; p < map.length - 1; p++) {
            dump.append('{').append(p);
            for (int q = p + 1; q < map.length; q++) {
                if (connected(p, q)) {
                    dump.append(',').append(q);
                }
            }
            dump.append('}');
        }
        return dump.toString();
    }
}
