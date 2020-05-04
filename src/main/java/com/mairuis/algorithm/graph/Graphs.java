package com.mairuis.algorithm.graph;


import java.util.*;

/**
 * 图相关算法
 *
 * @author Mairuis
 * @date 2019/6/9
 */
public class Graphs {

    /**
     * 查找环-DFS实现
     *
     * @param adjTable
     * @return 环链
     */
    public static List<Integer> getCycle(AdjTable adjTable) {
        Set<Integer> markSet = new HashSet<>();
        Stack<Integer> vertexStack = new Stack<>();
        Map<Integer, Integer> paths = new HashMap<>(adjTable.vertexSet().size());
        vertexStack.add(adjTable.getEntry());
        while (!vertexStack.isEmpty()) {
            int vertex = vertexStack.pop();
            markSet.add(vertex);
            for (int adj : adjTable.adj(vertex)) {
                //第一次探索该节点，标记并入栈，同时记录路径
                if (!markSet.contains(adj)) {
                    vertexStack.add(adj);
                    paths.put(adj, vertex);
                } else {
                    //发现环
                    List<Integer> cycle = new LinkedList<>();
                    for (int v = vertex; v != adj; v = paths.get(v)) {
                        cycle.add(v);
                    }
                    cycle.add(adj);
                    cycle.add(vertex);
                    return cycle;
                }
            }
        }
        return null;
    }

    /**
     * 有向图是否有环
     *
     * @param adjTable
     * @return
     */
    public static boolean hasCycle(AdjTable adjTable) {
        return getCycle(adjTable) != null;
    }

    /**
     * 返回该图的反向图
     *
     * @param adjTable
     * @return
     */
    public static AdjTable getReverse(AdjTable adjTable) {
        AdjTable rAdjTable = new AdjTable();
        for (int v : adjTable.vertexSet()) {
            for (int adj : adjTable.adj(v)) {
                rAdjTable.edge(adj, v);
            }
        }
        return rAdjTable;
    }

    /**
     * 拓扑排序实现-队列实现
     */
    public static Queue<Integer> topologicalByQueue(AdjTable adjTable) {
        Queue<Integer> source = new LinkedList<>(adjTable.getSource());
        Queue<Integer> order = new LinkedList<>();
        Map<Integer, Integer> degreeTable = adjTable.getInDegreeTable();
        int count = 0;
        while (!source.isEmpty()) {
            int v = source.poll();
            order.add(v);
            count += 1;
            for (int adj : adjTable.adj(v)) {
                int inDegree = degreeTable.get(adj) - 1;
                degreeTable.put(adj, inDegree);
                if (inDegree <= 0) {
                    source.add(adj);
                }
            }
        }
        //有环
        if (count != adjTable.vertexSet().size()) {
            order = null;
        }
        return order;
    }

    /**
     * 拓扑排序-DFS实现
     *
     * @param adjTable
     * @return
     */
    public static Stack<Integer> topologicalByDfs(AdjTable adjTable) {
        return topologicalByDfs(adjTable.getEntry(), new Stack<>(), adjTable, new HashSet<>());
    }

    public static Stack<Integer> topologicalByDfs(int vertex, Stack<Integer> stack, AdjTable adjTable, Set<Integer> marked) {
        stack.push(vertex);
        marked.add(vertex);
        for (int adj : adjTable.adj(vertex)) {
            if (!marked.contains(adj)) {
                topologicalByDfs(adj, stack, adjTable, marked);
            }
        }
        return stack;
    }


    /**
     * 求解环问题
     */
    public static void cycle() {
        for (int i : Objects.requireNonNull(getCycle(getAdjTable()))) {
            System.out.println(i);
        }
    }

    public static void main(String[] args) {
        Runnable a = () -> {
            Stack<Integer> stack = topologicalByDfs(getAdjTable());
            while (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
        };
        new Thread(Thread.currentThread().getThreadGroup(), a, "a", 1024).start();
    }

    public static AdjTable getAdjTable() {
        AdjTable adjTable = new AdjTable();

        adjTable.edge(0, 1);
        adjTable.edge(1, 2);
        adjTable.edge(1, 3);
        adjTable.edge(3, 4);
        adjTable.setEntry(0);

        //make cycle
        adjTable.edge(4, 1);
        return adjTable;
    }
}
