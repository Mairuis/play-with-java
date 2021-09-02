package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAConverter implements RegularExpressionConverter<NFAModel> {

    @Override
    public NFAModel convertConcatenation(ConcatenationExpression expression) {
        return null;
    }

    @Override
    public NFAModel convertIteration(IterationExpression iterationExpression) {
        final NFAModel innerNfaModel = convert(iterationExpression.getInnerExpression());
        return innerNfaModel;
    }

    @Override
    public NFAModel convertLiteral(LiteralExpression literalExpression) {
        return null;
    }

    @Override
    public NFAModel convertSymbol(SymbolExpression symbolExpression) {
        final char character = symbolExpression.getCharacter();
        final NFAState tailState = NFAState.create();
        final NFAEdge entryEdge = NFAEdge.create(character, tailState);
        return NFAModel.create(entryEdge, tailState);
    }

    @Override
    public NFAModel convertUnion(UnionExpression unionExpression) {
        return null;
    }
}
