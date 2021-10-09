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

    public static DFAModel convert(NFAModel nfaModel) {
        final Queue<NFASet> stateQueue = new LinkedList<>();
        final Set<NFASet> nfaSets = new HashSet<>();
        final Map<NFASet, Map<Character, NFASet>> dfaTable = new HashMap<>();
        stateQueue.add(epsilonClosure(new NFASet(nfaModel.getEntryEdge().getState())));
        while (!stateQueue.isEmpty()) {
            final NFASet nfaSet = stateQueue.poll();
            for (Character character : nfaModel.getInputSet()) {
                final NFASet nextSet = epsilonClosure(reachable(nfaSet, character));
                if (!nfaSets.contains(nextSet)) {
                    dfaTable.computeIfAbsent(nfaSet, (k) -> new HashMap<>()).put(character, nextSet);
                    stateQueue.add(nextSet);
                    nfaSets.add(nextSet);
                }
            }
        }
        return new DFAModel(dfaTable);
    }

    public static NFASet reachable(NFASet states, char character) {
        final NFASet reachableSet = new NFASet();
        for (NFAState state : states) {
            for (NFAEdge outEdge : state.getOutEdges()) {
                if (outEdge.getCharacter() == character) {
                    reachableSet.add(outEdge.getState());
                }
            }
        }
        return reachableSet;
    }

    public static NFASet epsilonClosure(NFASet states) {
        final Queue<NFAState> searchQueue = new LinkedList<>(states);
        final NFASet epsilonClosure = new NFASet(states);
        while (!searchQueue.isEmpty()) {
            final NFAState nextState = searchQueue.poll();
            final List<NFAEdge> outEdges = nextState.getOutEdges();
            for (NFAEdge outEdge : outEdges) {
                if (outEdge.getCharacter() != Symbols.EMPTY) {
                    continue;
                }
                final NFAState epsilonState = outEdge.getState();
                if (!epsilonClosure.contains(epsilonState)) {
                    searchQueue.offer(epsilonState);
                    epsilonClosure.add(epsilonState);
                }
            }
        }
        return epsilonClosure;
    }
}
