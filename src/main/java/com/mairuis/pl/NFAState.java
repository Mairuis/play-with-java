package com.mairuis.pl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public class NFAState {

    private static final AtomicInteger ID = new AtomicInteger(1);

    private final int id = ID.getAndIncrement();

    private final List<NFAEdge> outEdges = new ArrayList<>();

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NFAState nfaState = (NFAState) o;
        return id == nfaState.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

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
