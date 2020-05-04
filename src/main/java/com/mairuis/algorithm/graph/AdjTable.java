package com.mairuis.algorithm.graph;

import java.util.*;

/**
 * 邻接表
 * 既可以表示一个有向图也可以表示一个无向图
 *
 * @author Mairuis
 * @date 2019/6/6
 */
public class AdjTable {

    /**
     * <顶点,顶点的出边所指向的顶点集合>
     */
    private Map<Integer, List<Integer>> table;

    /**
     * <顶点,顶点的入边数>
     */
    private Map<Integer, Integer> inDegreeTable;

    /**
     * 顶点集合
     */
    private Set<Integer> vertexSet;

    /**
     * 边数
     */
    private int edge;

    /**
     * 入口节点
     */
    private Integer entry;

    /**
     * 构造一个邻接表
     */
    public AdjTable() {
        this.table = new HashMap<>();
        this.inDegreeTable = new HashMap<>();
        this.vertexSet = new HashSet<>();
    }

    public static AdjTable newAdjTable(int[][] edges) {
        AdjTable table = new AdjTable();
        for (int[] edge : edges) {
            table.edge(edge[0], edge[1]);
        }
        return table;
    }

    /**
     * 获取该节点的邻接节点
     *
     * @param vertex
     * @return
     */
    public List<Integer> adj(Integer vertex) {
        return table.getOrDefault(vertex, new LinkedList<>());
    }

    /**
     * 获取该节点的入度
     *
     * @param vertex
     * @return
     */
    public int getInDegree(Integer vertex) {
        return inDegreeTable.getOrDefault(vertex, 0);
    }

    /**
     * 获取该节点的出度
     *
     * @param vertex
     * @return
     */
    public int getOutDegree(Integer vertex) {
        return table.containsKey(vertex) ? table.get(vertex).size() : 0;
    }

    /**
     * 添加两条边,连通两个节点
     *
     * @param a
     * @param b
     */
    public void union(Integer a, Integer b) {
        table.get(a).add(b);
        table.get(b).add(a);
        edge += 2;

        vertexSet.add(a);
        vertexSet.add(b);
    }

    /**
     * 添加一条从a到b的有向边
     *
     * @param a
     * @param b
     */
    public void edge(Integer a, Integer b) {
        table.computeIfAbsent(a, (key) -> new ArrayList<>()).add(b);
        inDegreeTable.put(b, inDegreeTable.getOrDefault(b, 0) + 1);
        edge += 1;

        vertexSet.add(a);
        vertexSet.add(b);
    }

    /**
     * 所有顶点的集合
     *
     * @return
     */
    public Set<Integer> vertexSet() {
        return vertexSet;
    }

    /**
     * 获取边数
     *
     * @return
     */
    public int getEdge() {
        return edge;
    }

    /**
     * 获取入口
     *
     * @return
     */
    public Integer getEntry() {
        return entry;
    }

    public void setEntry(int i) {
        if (vertexSet.contains(i)) {
            this.entry = i;
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 获取所有终点
     * 出度为0
     * 起点为出度为0的顶点
     *
     * @return
     */
    public Set<Integer> getSink() {
        Set<Integer> sink = new HashSet<>();
        for (int v : vertexSet()) {
            if (getOutDegree(v) <= 0) {
                sink.add(v);
            }
        }
        return sink;
    }

    /**
     * 获取所有起点
     * 起点为入度为0的顶点
     *
     * @return
     */
    public Set<Integer> getSource() {
        Set<Integer> source = new HashSet<>();
        for (int v : vertexSet()) {
            if (getInDegree(v) <= 0) {
                source.add(v);
            }
        }
        return source;
    }

    /**
     * 获取入度表
     *
     * @return
     */
    public Map<Integer, Integer> getInDegreeTable() {
        return new HashMap<>(inDegreeTable);
    }

    /**
     * 打印邻接表
     */
    public void print() {
        for (Integer i : table.keySet()) {
            StringBuilder row = new StringBuilder(i + ":");
            for (Integer adj : table.getOrDefault(i, new LinkedList<>())) {
                row.append(adj.toString()).append("\t");
            }
            System.out.println(row);
        }
    }
}
