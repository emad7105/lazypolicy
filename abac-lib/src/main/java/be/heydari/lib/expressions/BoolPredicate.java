package be.heydari.lib.expressions;


import lombok.Builder;
import lombok.Data;

/**
 * @author Emad Heydari Beni
 *
 */
@Data
@Builder
public class BoolPredicate<T> {

    private RefExpression left;
    private GenericExpression<T> right;
    private ComparisonOperator operator;


    public BoolPredicate() {
    }

    public BoolPredicate(RefExpression left, GenericExpression<T> right, ComparisonOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
