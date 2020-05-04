package com.mairuis.algorithm.graph;

/**
 * 描述
 *
 * @author Mairuis
 * @date 2019/6/26
 */
public class WeightEdge implements Comparable<WeightEdge> {
    private int v;
    private int w;
    private float weight;

    public WeightEdge(int v, int w, float weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int other(int v) {
        if (v == w) {
            return this.v;
        } else if (v == this.v) {
            return this.w;
        } else {
            throw new IllegalStateException();
        }
    }

    public float getWeight() {
        return weight;
    }

    public int getV() {
        return v;
    }

    public int getW() {
        return w;
    }

    @Override
    public int compareTo(WeightEdge o) {
        return Double.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return getV() + "->" + getW();
    }
}