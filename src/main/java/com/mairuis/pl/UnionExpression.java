package com.mairuis.pl;

import java.util.Arrays;

/**
 * 并集操作
 * 表达式：(ab)*
 * 例子：ababab ab
 *
 * @author Mairuis
 * @since 2021/8/30
 */
public class UnionExpression implements RegularExpression {

    private final RegularExpression[] expressions;

    public UnionExpression(RegularExpression[] expressions) {
        this.expressions = expressions;
    }

    public RegularExpression[] getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        return Arrays.toString(expressions);
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.convertUnion(this);
    }
}
