package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public final class RegExp {

    public static RegularExpression symbol(char symbol) {
        return new SymbolExpression(symbol);
    }

    public static RegularExpression concatenation(RegularExpression left, RegularExpression right) {
        return new ConcatenationExpression(left, right);
    }

    public static RegularExpression union(RegularExpression... expressions) {
        return new UnionExpression(expressions);
    }

    public static RegularExpression iteration(RegularExpression expression) {
        return new IterationExpression(expression);
    }

    public static void main(String[] args) {
        final RegularExpression expression = union(iteration(concatenation(symbol('a'), symbol('b'))), symbol('a'));
        final NFAConverter nfaConverter = new NFAConverter();
        final NFAModel model = nfaConverter.convert(expression);
        final DFAModel dfaModel = DFAConverter.convert(model);
        System.out.println(dfaModel);
    }
}
