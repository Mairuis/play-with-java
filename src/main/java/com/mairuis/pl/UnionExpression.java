package com.mairuis.pl;

import java.util.Arrays;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public class UnionExpression implements RegularExpression {

    private final char[] chars;

    public UnionExpression(char[] chars) {
        this.chars = chars;
    }

    @Override
    public String toString() {
        return "[" + Arrays.toString(chars) + "]";
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.ConvertUnion(this);
    }
}
