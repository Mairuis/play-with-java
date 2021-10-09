package com.mairuis.pl;

import java.util.*;

/**
 * @author Mairuis
 * @since 2021/9/26
 */
public class NFASet extends AbstractSet<NFAState> {

    private final SortedMap<Integer, NFAState> sortedMap;

    private boolean modified = true;

    private String cachedState;

    public NFASet() {
        this.sortedMap = new TreeMap<>();
    }

    public NFASet(NFAState... states) {
        this.sortedMap = new TreeMap<>();
        this.addAll(Arrays.asList(states));
    }

    public NFASet(NFASet nfaSet) {
        this.sortedMap = new TreeMap<>(nfaSet.getSortedMap());
    }

    public String getId() {
        return toString();
    }

    private SortedMap<Integer, NFAState> getSortedMap() {
        return sortedMap;
    }

    @Override
    public Iterator<NFAState> iterator() {
        return this.getSortedMap().values().iterator();
    }

    @Override
    public int size() {
        return this.getSortedMap().size();
    }

    @Override
    public boolean add(NFAState nfaState) {
        final boolean contained = this.getSortedMap().put(nfaState.getId(), nfaState) != null;
        this.modified = modified || !contained;
        return contained;
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof NFAState) {
            this.modified = true;
            return this.getSortedMap().remove(((NFAState) o).getId()) != null;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        if (o instanceof NFAState) {
            return this.getSortedMap().containsKey(((NFAState) o).getId());
        }
        return false;
    }

    @Override
    public String toString() {
        if (this.modified) {
            final StringBuilder builder = new StringBuilder();
            for (Integer id : sortedMap.keySet()) {
                builder.append(id);
            }
            this.cachedState = builder.toString();
            this.modified = false;
        }
        return this.cachedState;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NFASet nfaStates = (NFASet) o;
        return cachedState.equals(nfaStates.cachedState);
    }
}
