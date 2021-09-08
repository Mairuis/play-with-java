package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public class NFAEdge {
    private final char character;
    private final NFAState state;

    public NFAEdge(NFAState state) {
        this.character = Symbols.EMPTY;
        this.state = state;
    }

    public NFAEdge(char character, NFAState state) {
        this.character = character;
        this.state = state;
    }

    public char getCharacter() {
        return character;
    }

    public NFAState getState() {
        return state;
    }

    public static NFAEdge create(char character, NFAState state) {
        return new NFAEdge(character, state);
    }

}
