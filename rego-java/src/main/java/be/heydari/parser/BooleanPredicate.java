package be.heydari.parser;

import lombok.Builder;
import lombok.Data;

/**
 * @author Emad Heydari Beni
 *
 * @param <L>
 * @param <R>
 */
@Data
@Builder
public class BooleanPredicate<L,R> implements Visitable {

    private GenericExpression<L> left;
    private GenericExpression<R> right;
    private ComparisonOperator operator;

    @Override
    public String accept(Visitor visitor) {
        return visitor.visit(this);
    }
}
