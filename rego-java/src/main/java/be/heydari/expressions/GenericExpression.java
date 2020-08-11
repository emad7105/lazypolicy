package be.heydari.expressions;

import lombok.Data;

/**
 * For data types:
 *  - Numeric
 *  - String
 *  - Boolean
 *
 * @author Emad Heyari Beni
 * @param <T> can be String, Boolean, Double
 */
@Data
public class GenericExpression<T> {
    private ExpressionType type;
    private T value;
}