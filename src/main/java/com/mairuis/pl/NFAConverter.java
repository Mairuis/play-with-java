package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAConverter implements RegularExpressionConverter<NFAModel> {

    @Override
    public NFAModel convertConcatenation(ConcatenationExpression expression) {
        final NFAModel leftInnerNfaModel = convert(expression.getLeft());
        final NFAModel rightInnerNfaModel = convert(expression.getRight());
        final NFAState entryState = NFAState.create();
        final NFAState tailState = NFAState.create();
        final NFAEdge entryEdge = NFAEdge.create(Symbols.EMPTY, entryState);
        entryState.addEdge(leftInnerNfaModel.getEntryEdge());
        entryState.addEdge(rightInnerNfaModel.getEntryEdge());
        leftInnerNfaModel.getTailState().addEdge(NFAEdge.create(Symbols.EMPTY, tailState));
        rightInnerNfaModel.getTailState().addEdge(NFAEdge.create(Symbols.EMPTY, tailState));
        return NFAModel.create(entryEdge, tailState);
    }

    @Override
    public NFAModel convertIteration(IterationExpression iterationExpression) {
        final NFAModel innerNfaModel = convert(iterationExpression.getInnerExpression());
        final NFAState tailState = NFAState.create();
        final NFAState entryState = NFAState.create();
        final NFAState innerTailState = innerNfaModel.getTailState();
        final NFAEdge entryEdge = NFAEdge.create(Symbols.EMPTY, entryState);
        entryState.addEdge(innerNfaModel.getEntryEdge());
        entryState.addEdge(NFAEdge.create(Symbols.EMPTY, tailState));
        tailState.addEdge(NFAEdge.create(Symbols.EMPTY, entryState));
        innerTailState.addEdge(NFAEdge.create(Symbols.EMPTY, tailState));
        return NFAModel.create(entryEdge, tailState);
    }

    @Override
    public NFAModel convertLiteral(LiteralExpression literalExpression) {
        return null;
    }

    @Override
    public NFAModel convertSymbol(SymbolExpression symbolExpression) {
        final char character = symbolExpression.getCharacter();
        final NFAState entryState = NFAState.create();
        final NFAEdge entryEdge = NFAEdge.create(Symbols.EMPTY, entryState);
        final NFAState tailState = NFAState.create();
        entryState.addEdge(NFAEdge.create(character, tailState));
        return NFAModel.create(entryEdge, tailState);
    }

    @Override
    public NFAModel convertUnion(UnionExpression unionExpression) {
        return null;
    }
}
