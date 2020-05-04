package com.mairuis.algorithm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 带权重的邻接表
 *
 * @author Mairuis
 * @date 2019/6/19
 */
public class WeightAdjTable {

    private Map<Integer, List<WeightEdge>> adjTable;

    private int edgeCount;

    public WeightAdjTable() {
        adjTable = new HashMap<>();
    }

    public List<WeightEdge> adj(int v) {
        return adjTable.computeIfAbsent(v, (k) -> new ArrayList<>());
    }

    public WeightEdge edge(int v, int w, float weight) {
        WeightEdge weightEdge = new WeightEdge(v, w, weight);
        this.adjTable.computeIfAbsent(w, (k) -> new ArrayList<>()).add(weightEdge);
        this.adjTable.computeIfAbsent(v, (k) -> new ArrayList<>()).add(weightEdge);
        this.edgeCount += 1;
        return weightEdge;
    }

    public int edgeCount() {
        return edgeCount;
    }

    public int vertexCount() {
        return adjTable.size();
    }
}
