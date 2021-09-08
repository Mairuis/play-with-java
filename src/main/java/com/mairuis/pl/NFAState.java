package com.mairuis.pl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public class NFAState {

    private final List<NFAEdge> outEdges = new ArrayList<>();

    public void addEdge(NFAEdge edge) {
        outEdges.add(edge);
    }

    public List<NFAEdge> getOutEdges() {
        return outEdges;
    }

    public static NFAState create() {
        return new NFAState();
    }

}
