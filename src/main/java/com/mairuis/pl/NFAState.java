package com.mairuis.pl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public class NFAState {

    private final List<NFAEdge> outEdges = new ArrayList<>();

    public static NFAState create() {
        return new NFAState();
    }

    public void addEdge(NFAEdge edge) {
        outEdges.add(edge);
    }

}
