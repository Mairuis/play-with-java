package com.mairuis.algorithm.graph;

import java.util.*;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/6/21
 */
public class PrimMST {
    private WeightAdjTable adjTable;
    private PriorityQueue<WeightEdge> minQueue;
    private Queue<WeightEdge> mst;
    private Set<Integer> checkVertex;

    public PrimMST(WeightAdjTable adjTable) {
        this.adjTable = adjTable;
        this.mst = new LinkedList<>();
        this.checkVertex = new HashSet<>();
        this.minQueue = new PriorityQueue<>(adjTable.edgeCount());
    }

    public static void main(String[] args) {
        WeightAdjTable adjTable = getAdjTable();
        PrimMST mst = new PrimMST(adjTable).build(0);
        for (WeightEdge edge : mst.getEdge()) {
            System.out.println(edge);
        }
    }

    public static WeightAdjTable getAdjTable() {
        WeightAdjTable adjTable = new WeightAdjTable();
        adjTable.edge(4, 5, 0.35f);
        adjTable.edge(4, 7, 0.37f);
        adjTable.edge(5, 7, 0.28f);
        adjTable.edge(0, 7, 0.16f);
        adjTable.edge(1, 5, 0.32f);
        adjTable.edge(0, 4, 0.38f);
        adjTable.edge(2, 3, 0.17f);
        adjTable.edge(1, 7, 0.19f);
        adjTable.edge(0, 2, 0.26f);
        adjTable.edge(1, 2, 0.36f);
        adjTable.edge(1, 3, 0.29f);
        adjTable.edge(2, 7, 0.34f);
        adjTable.edge(6, 2, 0.40f);
        adjTable.edge(3, 6, 0.52f);
        adjTable.edge(6, 0, 0.58f);
        adjTable.edge(6, 4, 0.93f);
        return adjTable;
    }

    public PrimMST build(int v) {
        this.visit(v);
        while (!minQueue.isEmpty()) {
            WeightEdge minE = minQueue.poll();
            int edgeV = minE.getV();
            int edgeW = minE.other(edgeV);
            if (checkVertex.contains(edgeV) && checkVertex.contains(edgeW)) {
                continue;
            }
            if (adjTable.vertexCount() == mst.size()) {
                break;
            }
            mst.add(minE);
            if (!checkVertex.contains(edgeW)) {
                this.visit(edgeW);
            }
            if (!checkVertex.contains(edgeV)) {
                this.visit(edgeV);
            }
        }
        return this;
    }

    public void visit(int v) {
        checkVertex.add(v);
        for (WeightEdge edge : adjTable.adj(v)) {
            if (!checkVertex.contains(edge.other(v))) {
                minQueue.add(edge);
            }
        }
    }

    private Iterable<? extends WeightEdge> getEdge() {
        return mst;
    }
}
