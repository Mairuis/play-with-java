package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/8/30
 */
public class SymbolExpression implements RegularExpression {

    private final char character;

    public SymbolExpression(char character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return String.valueOf(character);
    }

    @Override
    public <T> T Accept(RegularExpressionConverter<T> visitor) {
        return visitor.ConvertSymbol(this);
    }
}
