package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/31
 */
public interface RegularExpressionConverter<T> {

    T ConvertConcatenation(ConcatenationExpression expression);

    T ConvertIteration(IterationExpression iterationExpression);

    T ConvertLiteral(LiteralExpression literalExpression);

    T ConvertSymbol(SymbolExpression symbolExpression);

    T ConvertUnion(UnionExpression unionExpression);
    
}
