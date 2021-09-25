package com.mairuis.pl;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 子集构造算法实现 (subset construction)
 * 编译原理 97页 算法3.20
 *
 * @author Mairuis
 * @since 2021/9/22
 */
public class DFAConverter implements RegularExpressionConverter<DFAModel> {
    @Override
    public DFAModel convertConcatenation(ConcatenationExpression expression) {
        return null;
    }

    @Override
    public DFAModel convertIteration(IterationExpression iterationExpression) {
        return null;
    }

    @Override
    public DFAModel convertLiteral(LiteralExpression literalExpression) {
        return null;
    }

    @Override
    public DFAModel convertSymbol(SymbolExpression symbolExpression) {
        return null;
    }

    @Override
    public DFAModel convertUnion(UnionExpression unionExpression) {
        return null;
    }

    private Set<NFAState> reachable(Set<NFAState> states, char character) {
        final Queue<NFAState> searchQueue = new LinkedList<>(states);
        while (!searchQueue.isEmpty()) {
            final NFAState state = searchQueue.poll();
            
        }
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
