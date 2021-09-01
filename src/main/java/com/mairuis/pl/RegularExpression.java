package com.mairuis.pl;

/**
 * @author Mairuis
 * @since 2021/9/1
 */
public interface RegularExpression {

    <T> T Accept(RegularExpressionConverter<T> visitor);

}
