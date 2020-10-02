package be.heydari.lib.expressions;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Emad Heyari Beni
 * @param <T> can be String, Boolean, Double, `
 */
@Data
@Builder
@NoArgsConstructor
public class GenericExpression<T> {
    private ExpressionType type;
    private T value;
}
