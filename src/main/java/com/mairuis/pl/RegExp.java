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

    public static RegularExpression concatenation(RegularExpression... expressions) {
        RegularExpression expression = null;
        for (RegularExpression element : expressions) {
            if (expression == null) {
                expression = element;
            } else {
                expression = new ConcatenationExpression(expression, element);
            }
        }
        return expression;
    }

    public static RegularExpression literal(String literal) {
        return new LiteralExpression(literal);
    }

    public static RegularExpression union(char... chars) {
        return new UnionExpression(chars);
    }

    public static RegularExpression iteration(RegularExpression expression) {
        return new IterationExpression(expression);
    }

    public static void main(String[] args) {
        final RegularExpression expression = iteration(concatenation(symbol('a'), symbol('b')));
        final NFAConverter nfaConverter = new NFAConverter();
        final NFAModel model = nfaConverter.convert(expression);
        System.out.println(model.toString());
    }
}
