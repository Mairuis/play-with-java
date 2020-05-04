package com.mairuis.algorithm.graph;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/6/26
 */
public class WeightDirectedEdge implements Comparable<WeightDirectedEdge> {
    private int x;
    private int y;
    private float weight;

    WeightDirectedEdge(int x, int y, float weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public int from() {
        return x;
    }

    public int to() {
        return y;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public int compareTo(WeightDirectedEdge o) {
        return Float.compare(getWeight(), o.getWeight());

    }
}
