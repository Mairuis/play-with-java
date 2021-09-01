package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public class NFAEdge {
    private final char character;
    private final NFAState state;

    public NFAEdge(char character, NFAState state) {
        this.character = character;
        this.state = state;
    }
}
