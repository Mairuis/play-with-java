package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public class ConcatenationExpression implements RegularExpression {

    private final RegularExpression left;
    private final RegularExpression right;

    public ConcatenationExpression(RegularExpression left, RegularExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + right.toString();
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.convertConcatenation(this);
    }
}
