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
}
