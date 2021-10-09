package com.mairuis.pl;

import java.util.Map;

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
}
