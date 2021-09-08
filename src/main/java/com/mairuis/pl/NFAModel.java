package com.mairuis.pl;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAModel {

    private final NFAEdge entryEdge;

    private final NFAState tailState;

    public NFAModel(NFAEdge entryEdge, NFAState tailState) {
        this.entryEdge = entryEdge;
        this.tailState = tailState;
    }

    public static NFAModel create(NFAEdge entryEdge, NFAState tailState) {
        return new NFAModel(entryEdge, tailState);
    }

    public NFAEdge getEntryEdge() {
        return entryEdge;
    }

    public NFAState getTailState() {
        return tailState;
    }

    @Override
    public String toString() {
        final AtomicInteger id = new AtomicInteger();
        final Map<NFAState, Integer> codeMap = new HashMap<>();
        final Queue<NFAState> states = new LinkedList<>();
        final Set<Integer> rememberState = new HashSet<>();
        final Map<Integer, Map<Character, Set<Integer>>> nfaTable = new HashMap<>();
        states.add(entryEdge.getState());
        while (!states.isEmpty()) {
            final NFAState state = states.poll();
            final Integer stateId = codeMap.computeIfAbsent(state, (nfaState) -> id.incrementAndGet());
            for (final NFAEdge edge : state.getOutEdges()) {
                final NFAState toState = edge.getState();
                final Integer toStateId = codeMap.computeIfAbsent(toState, (nfaState) -> id.incrementAndGet());
                nfaTable.computeIfAbsent(stateId, (k) -> new HashMap<>()).computeIfAbsent(edge.getCharacter(), (k) -> new HashSet<>()).add(toStateId);

                if (!rememberState.contains(toStateId)) {
                    states.add(toState);
                    rememberState.add(toStateId);
                }
            }
        }
        return JSON.toJSONString(nfaTable);
    }
}
