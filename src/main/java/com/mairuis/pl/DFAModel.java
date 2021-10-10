package com.mairuis.pl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Mairuis
 * @since 2021/9/22
 */
public class DFAModel {

    private final Map<NFASet, Map<Character, NFASet>> dfaTable;

    public DFAModel(Map<NFASet, Map<Character, NFASet>> dfaTable) {
        this.dfaTable = dfaTable;
    }

    public Map<NFASet, Map<Character, NFASet>> getDfaTable() {
        return dfaTable;
    }

    @Override
    public String toString() {
        final Set<Character> characters = dfaTable.values().stream().map(Map::keySet).flatMap(Collection::stream).collect(Collectors.toSet());
        final StringBuilder builder = new StringBuilder("\t");
        characters.forEach(character -> builder.append(character).append('\t'));
        builder.append('\n');
        dfaTable.keySet().forEach(nfaSet -> {
            builder.append(nfaSet).append('\t');
            characters.stream().map(character -> dfaTable.get(nfaSet).getOrDefault(character, NFASet.EMPTY)).forEach(transform -> builder.append(transform).append('\t'));
            builder.append('\n');
        });
        return builder.toString();
    }
}
