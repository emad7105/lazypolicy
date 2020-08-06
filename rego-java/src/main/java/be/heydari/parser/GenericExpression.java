package be.heydari.parser;

import lombok.Data;

/**
 * For data types:
 *  - Numeric
 *  - Text: value of a column, or column/table name
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
