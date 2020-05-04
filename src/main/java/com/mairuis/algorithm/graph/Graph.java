package com.mairuis.algorithm.graph;

import java.util.*;

/**
 * 图
 *
 * @author Mairuis
 * @date 2019/5/31
 */
public class Graph extends AbstractGraph {

    private final int START_VERTEX;
    /**
     * 这里储存着顶点和可以到达该顶点的顶点
     * <顶点,可达顶点>
     */
    private Map<Integer, Integer> paths;
    /**
     * <顶点,顶点所在的连通图Id>
     */
    private Map<Integer, Integer> connect;
    /**
     * 连通图的数量
     */
    private int graphCount;

    public Graph(int startVertex, AdjTable adjTable) {
        super(adjTable);
        this.START_VERTEX = startVertex;
        this.paths = new HashMap<>();
        this.graphCount = 0;
        this.connect = new HashMap<>(0);
        this.paths = new HashMap<>(adjTable.getEdge());

        Set<Integer> check = new HashSet<>(adjTable.getEdge());
        for (int v : getGraph().vertexSet()) {
            if (!check.contains(v)) {
                this.search(v, check);
                this.graphCount += 1;
            }
        }
    }

    public static void main(String[] args) {
        AdjTable adjTable = new AdjTable();

        adjTable.edge(1, 2);
        adjTable.edge(1, 3);
        adjTable.edge(1, 4);
        adjTable.edge(3, 4);
        adjTable.edge(2, 5);
        adjTable.edge(3, 5);

        adjTable.edge(6, 7);

        Graph graph = new Graph(1, adjTable);
        System.out.println(graph.hasPath(4));

        Stack<Integer> path = graph.getPath(5);
        while (!path.isEmpty()) {
            System.out.println(path.pop());
        }

        System.out.println("hasPath " + graph.hasPath(6));
        System.out.println("isConnected 6 7" + graph.isConnected(6, 7));
        System.out.println("isConnected 7 7" + graph.isConnected(7, 7));
        System.out.println("isConnected 1 5" + graph.isConnected(1, 5));
    }

    @Override
    public boolean isConnected(int a, int b) {
        return this.connect.getOrDefault(a, -1).equals(this.connect.getOrDefault(b, -2));
    }

    @Override
    public boolean hasPath(int vertex) {
        return paths.containsKey(vertex);
    }

    public int getGraphCount() {
        return graphCount;
    }

    @Override
    public Stack<Integer> getPath(int vertex) {
        if (hasPath(vertex)) {
            Stack<Integer> pathNode = new Stack<>();
            for (int i = vertex; i != START_VERTEX; i = paths.get(i)) {
                pathNode.push(i);
            }
            pathNode.push(START_VERTEX);
            return pathNode;
        }
        return new Stack<>();
    }

    private void search(int vertex, Set<Integer> check) {
        this.deepFirstSearch(vertex, check);
    }

    /**
     * 深度优先搜索
     *
     * @param startVertex
     * @param check
     */
    private void deepFirstSearch(int startVertex, Set<Integer> check) {
        Stack<Integer> task = new Stack<>();
        task.push(startVertex);
        while (!task.isEmpty()) {
            int vertex = task.pop();
            check.add(vertex);
            connect.put(vertex, getGraphCount());
            for (int adj : this.getGraph().adj(vertex)) {
                if (!check.contains(adj)) {
                    paths.put(adj, vertex);
                    connect.put(adj, getGraphCount());
                    task.push(adj);
                }
            }
        }
    }

    /**
     * 广度优先搜索
     *
     * @param startVertex
     * @param check
     */
    private void breadthFirstSearch(int startVertex, Set<Integer> check) {
        Queue<Integer> task = new LinkedList<>();
        task.add(startVertex);
        while (!task.isEmpty()) {
            int vertex = task.poll();
            check.add(vertex);
            connect.put(vertex, getGraphCount());
            for (int adj : this.getGraph().adj(vertex)) {
                if (!check.contains(adj)) {
                    paths.put(adj, vertex);
                    connect.put(adj, getGraphCount());
                    task.add(adj);
                }
            }
        }
    }
}
