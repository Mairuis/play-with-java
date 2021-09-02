package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public class IterationExpression implements RegularExpression {

    private final RegularExpression innerExpression;

    public IterationExpression(RegularExpression innerExpression) {
        this.innerExpression = innerExpression;
    }

    public RegularExpression getInnerExpression() {
        return innerExpression;
    }

    @Override
    public String toString() {
        return innerExpression.toString() + "*";
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.convertIteration(this);
    }
}
