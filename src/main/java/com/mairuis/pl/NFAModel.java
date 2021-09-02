package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAModel {

    private final NFAEdge entryEdge;

    private final NFAState endState;

    public NFAModel(NFAEdge entryEdge, NFAState endState) {
        this.entryEdge = entryEdge;
        this.endState = endState;
    }

    public static NFAModel create(NFAEdge entryEdge, NFAState tailState) {
        return new NFAModel(entryEdge, tailState);
    }

    public NFAEdge getEntryEdge() {
        return entryEdge;
    }

    public NFAState getEndState() {
        return endState;
    }
}
