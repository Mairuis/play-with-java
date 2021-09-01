package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public class LiteralExpression implements RegularExpression {

    private final String literal;

    public LiteralExpression(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.ConvertLiteral(this);
    }
}
