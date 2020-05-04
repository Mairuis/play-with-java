package com.mairuis.algorithm.graph;

import java.util.Stack;

/**
 * 图的抽象父类
 *
 * @author Mairuis
 * @date 2019/6/6
 */
public abstract class AbstractGraph {
    /**
     * 邻接表
     */
    private final AdjTable graph;

    /**
     * 构造一个以 entry 为起点的图
     */
    public AbstractGraph(AdjTable graph) {
        this.graph = graph;

    }

    public AdjTable getGraph() {
        return graph;
    }

    /**
     * 获取到该顶点的路径
     *
     * @param vertex
     * @return
     */
    public abstract Stack<Integer> getPath(int vertex);

    /**
     * 判断两个顶点是否连通
     *
     * @param a
     * @param b
     * @return
     */
    public abstract boolean isConnected(int a, int b);

    /**
     * 判断是否有路径到达该顶点
     */
    public abstract boolean hasPath(int vertex);
}
