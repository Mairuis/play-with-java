package com.mairuis.pl;

import java.util.*;

/**
 * 子集构造算法实现 (subset construction)
 * 编译原理 97页 算法3.20
 *
 * @author Mairuis
 * @since 2021/9/22
 */
public class DFAConverter {

    private Set<NFAState> reachable(Set<NFAState> states, char character) {
        final Set<NFAState> reachableSet = new HashSet<>();
        for (NFAState state : states) {
            for (NFAEdge outEdge : state.getOutEdges()) {
                if (outEdge.getCharacter() == character) {
                    reachableSet.add(outEdge.getState());
                }
            }
        }
        return reachableSet;
    }

    private Set<NFAState> epsilonClosure(Set<NFAState> states) {
        final Queue<NFAState> searchQueue = new LinkedList<>(states);
        while (!searchQueue.isEmpty()) {
            final NFAState nextState = searchQueue.poll();
            final List<NFAEdge> outEdges = nextState.getOutEdges();
            for (NFAEdge outEdge : outEdges) {
                if (outEdge.getCharacter() != Symbols.EMPTY) {
                    continue;
                }
                final NFAState epsilonState = outEdge.getState();
                if (!states.contains(epsilonState)) {
                    searchQueue.offer(epsilonState);
                    states.add(epsilonState);
                }
            }
        }
        return states;
    }
}
