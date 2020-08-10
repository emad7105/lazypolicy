package be.heydari.parsers;

import be.heydari.expressions.BoolPredicate;
import be.heydari.expressions.Conjunction;
import be.heydari.expressions.Disjunction;
import be.heydari.expressions.RefExpression;

/**
 * @author Emad Heydari Beni
 */
public interface Visitor<TEntity, TBoolExpression, TReference> {

    TBoolExpression visit(Conjunction conjunction, TEntity entity);
    TBoolExpression visit(Disjunction disjunction, TEntity entity);
    <TValueType> TBoolExpression visit(BoolPredicate<TValueType> predicate, TEntity entity);
    TReference visit(RefExpression<Long> refExpression, TEntity entity);
}
