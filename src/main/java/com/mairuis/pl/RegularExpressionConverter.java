package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/31
 */
public interface RegularExpressionConverter<T> {

    default T convert(RegularExpression expression) {
        return expression.Accept(this);
    }

    T convertConcatenation(ConcatenationExpression expression);

    T convertIteration(IterationExpression iterationExpression);

    T convertSymbol(SymbolExpression symbolExpression);

    T convertUnion(UnionExpression unionExpression);

}
