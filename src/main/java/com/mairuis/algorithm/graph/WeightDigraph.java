package com.mairuis.algorithm.graph;

import java.util.*;

/**
 * 有向权重图
 *
 * @author Mairuis
 * @date 2019/6/26
 */
public class WeightDigraph {

    private Map<Integer, List<WeightDirectedEdge>> adjTable;

    public WeightDigraph() {
        this.adjTable = new HashMap<>();
    }

    public static void main(String[] args) {

    }

    /**
     * 添加一条边，从w到v
     *
     * @param w
     * @param v
     * @param weight
     */
    public void addEdge(int w, int v, int weight) {
        WeightDirectedEdge weightDirectedEdge = new WeightDirectedEdge(w, v, weight);
        adjTable.computeIfAbsent(w, (k) -> new ArrayList<>()).add(weightDirectedEdge);
    }

    /**
     * 该顶点指向的所有顶点
     *
     * @param v
     * @return
     */
    public List<WeightDirectedEdge> adj(int v) {
        return adjTable.computeIfAbsent(v, (k) -> new ArrayList<>());
    }

    public int vertexCount() {
        return adjTable.size();
    }

    public Set<Integer> vertexSet() {
        return adjTable.keySet();
    }
}
