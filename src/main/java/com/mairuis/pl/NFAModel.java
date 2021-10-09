package com.mairuis.pl;

import com.alibaba.fastjson.JSON;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 汤普森构造法实现非确定状态机
 *
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAModel {

    /**
     * 入边
     * 永远为 epsilon 边
     */
    private final NFAEdge entryEdge;

    private final NFAState tailState;

    public NFAModel(NFAEdge entryEdge, NFAState tailState) {
        this.entryEdge = entryEdge;
        this.tailState = tailState;
    }

    public NFAEdge getEntryEdge() {
        return entryEdge;
    }

    public NFAState getTailState() {
        return tailState;
    }

    public Set<Character> getInputSet() {
        final Queue<NFAState> stateQueue = new LinkedList<>();
        final Set<NFAState> stateSet = new HashSet<>();
        final Set<Character> characterSet = new HashSet<>();
        characterSet.add(entryEdge.getCharacter());
        stateQueue.add(entryEdge.getState());
        stateSet.add(entryEdge.getState());

        while (!stateQueue.isEmpty()) {
            final NFAState state = stateQueue.poll();
            for (NFAEdge outEdge : state.getOutEdges()) {
                characterSet.add(outEdge.getCharacter());
                if (!stateSet.contains(outEdge.getState())) {
                    stateQueue.add(outEdge.getState());
                    stateSet.add(outEdge.getState());
                }
            }
        }
        return characterSet;
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

    public static NFAModel create(NFAEdge entryEdge, NFAState tailState) {
        return new NFAModel(entryEdge, tailState);
    }
}
