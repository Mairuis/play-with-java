package com.mairuis.pl;

import java.util.*;

/**
 * @author Mairuis
 * @since 2021/9/26
 */
public class NFASet extends AbstractSet<NFAState> {

    public static final NFASet EMPTY = new NFASet();

    private final BitSet bitSet = new BitSet();

    private final Set<NFAState> stateSet = new HashSet<>();

    public NFASet(NFAState... states) {
        this.addAll(Arrays.asList(states));
    }

    public NFASet(NFASet nfaSet) {
        this.bitSet.or(nfaSet.bitSet);
        this.stateSet.addAll(nfaSet.stateSet);
    }

    @Override
    public Iterator<NFAState> iterator() {
        return this.stateSet.iterator();
    }

    @Override
    public int size() {
        return this.stateSet.size();
    }

    @Override
    public boolean add(NFAState nfaState) {
        this.bitSet.set(nfaState.getId());
        return stateSet.add(nfaState);
    }

    @Override
    public boolean remove(Object o) {
        final NFAState nfaState = (NFAState) o;
        this.bitSet.clear(nfaState.getId());
        return this.stateSet.remove(nfaState);
    }

    @Override
    public boolean contains(Object o) {
        final NFAState nfaState = (NFAState) o;
        return this.bitSet.get(nfaState.getId());
    }

    @Override
    public String toString() {
        return bitSet.toString();
    }

    @Override
    public int hashCode() {
        return this.bitSet.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NFASet nfaStates = (NFASet) o;
        return bitSet.equals(nfaStates.bitSet);
    }
}
