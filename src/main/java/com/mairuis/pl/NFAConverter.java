package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/9
 */
public class NFAConverter implements RegularExpressionConverter<NFAModel> {

    @Override
    public NFAModel ConvertConcatenation(ConcatenationExpression expression) {
        return null;
    }

    @Override
    public NFAModel ConvertIteration(IterationExpression iterationExpression) {
        return null;
    }

    @Override
    public NFAModel ConvertLiteral(LiteralExpression literalExpression) {
        return null;
    }

    @Override
    public NFAModel ConvertSymbol(SymbolExpression symbolExpression) {
        return null;
    }

    @Override
    public NFAModel ConvertUnion(UnionExpression unionExpression) {
        return null;
    }
}
